package learn.microservices.multiplicator.challenge.service.impl;

import learn.microservices.multiplicator.challenge.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import learn.microservices.multiplicator.challenge.publisher.ChallengeSolvedEventPublisher;
import learn.microservices.multiplicator.challenge.repository.ChallengeAttemptRepository;
import learn.microservices.multiplicator.challenge.service.ChallengeService;
import learn.microservices.multiplicator.user.entity.User;
import learn.microservices.multiplicator.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {

    private ChallengeService challengeService;

    @Mock
    private ChallengeAttemptRepository challengeAttemptRepository;

    @Mock
    private UserService userService;

    @Mock
    private ChallengeSolvedEventPublisher challengeSolvedEventPublisher;

    private final User USER = new User("1", "test_1");

    private final ChallengeAttempt CORRECT_CHALLENGE_ATTEMPT = new ChallengeAttempt(
            "1",
            USER,
            20,
            30,
            600,
            true,
            System.currentTimeMillis() + 10
    );

    private final ChallengeAttempt WRONG_CHALLENGE_ATTEMPT = new ChallengeAttempt(
            "2",
            USER,
            20,
            30,
            500,
            false,
            System.currentTimeMillis()
    );

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(challengeAttemptRepository, userService, challengeSolvedEventPublisher);
    }

    @Test
    public void whenCorrectChallengeAttempt_thenIsCorrectTrue() {
        // given
        ChallengeAttemptDto dto = new ChallengeAttemptDto(20, 30, "test_1", 600);
        given(userService.create(any())).will(returnsFirstArg());
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());

        // when
        ChallengeAttempt result = challengeService.verifyAttempt(dto);

        // then
        then(result.isCorrect()).isTrue();
        verify(userService).create(new User("test_1"));
        verify(challengeAttemptRepository).save(result);
        verify(challengeSolvedEventPublisher).sendEvent(result);
    }

    @Test
    public void whenWrongChallengeAttempt_thenIsCorrectFalse() {
        // given
        ChallengeAttemptDto dto = new ChallengeAttemptDto(20, 30, "test_1", 500);
        given(userService.create(any())).will(returnsFirstArg());
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());

        // when
        ChallengeAttempt result = challengeService.verifyAttempt(dto);

        // then
        then(result.isCorrect()).isFalse();
        verify(userService).create(new User("test_1"));
        verify(challengeAttemptRepository).save(result);
        verify(challengeSolvedEventPublisher).sendEvent(result);
    }

    @Test
    public void whenVerifyChallengeAttemptAndUserByAliasExists_thenUserWillBeNeverCreated() {
        // given
        User existingUser = USER;
        given(userService.findByAlias(USER.getAlias())).willReturn(Optional.of(existingUser));
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDto dto = new ChallengeAttemptDto(20, 30, USER.getAlias(), 500);

        // when
        ChallengeAttempt result = challengeService.verifyAttempt(dto);

        // then
        then(result.isCorrect()).isFalse();
        then(result.getUser()).isEqualTo(existingUser);
        verify(userService, never()).create(any());
        verify(challengeAttemptRepository).save(result);
        verify(challengeSolvedEventPublisher).sendEvent(result);
    }

    @Test
    public void whenCreateChallengeAttempt_thenReturnsCorrectEntity() {
        // given
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());

        // when
        ChallengeAttempt createdChallengeAttempt = challengeService.create(CORRECT_CHALLENGE_ATTEMPT);

        // then
        then(createdChallengeAttempt).isEqualTo(CORRECT_CHALLENGE_ATTEMPT);
    }

    @Test
    public void whenFindAll_thenFoundSizeIsCorrect() {
        // given
        given(challengeAttemptRepository.findAll()).willReturn(List.of(CORRECT_CHALLENGE_ATTEMPT, WRONG_CHALLENGE_ATTEMPT));

        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findAll();

        // then
        then(foundChallengeAttempts.size()).isEqualTo(2);
    }

    @Test
    public void whenFindById_thenFoundIsCorrect() {
        // given
        given(challengeAttemptRepository.findById(anyString())).willReturn(Optional.of(CORRECT_CHALLENGE_ATTEMPT));

        // when
        Optional<ChallengeAttempt> foundChallengeAttempt = challengeService.findById(CORRECT_CHALLENGE_ATTEMPT.getId());

        // then
        then(foundChallengeAttempt.get()).isEqualTo(CORRECT_CHALLENGE_ATTEMPT);
    }

    @Test
    public void whenFindByUserId_thenFoundSizeIsCorrect() {
        // given
        given(challengeAttemptRepository.findByUserId(anyString())).willReturn(List.of(CORRECT_CHALLENGE_ATTEMPT, WRONG_CHALLENGE_ATTEMPT));

        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findByUserId(USER.getId());

        // then
        then(foundChallengeAttempts.size()).isEqualTo(2);
    }

    @Test
    public void whenFindByUserAlias_thenFoundSizeIsCorrect() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("timestamp").descending());
        given(challengeAttemptRepository.findAllByUserAliasOrderByTimestampDesc(anyString(), same(pageRequest)))
                .willReturn(List.of(CORRECT_CHALLENGE_ATTEMPT, WRONG_CHALLENGE_ATTEMPT));

        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findByUserAlias(USER.getAlias(), pageRequest);

        // then
        then(foundChallengeAttempts.size()).isEqualTo(2);
    }

    @Test
    public void whenFindByUserAlias_thenTimestampOrderDesc() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("timestamp").descending());
        given(challengeAttemptRepository.findAllByUserAliasOrderByTimestampDesc(anyString(), same(pageRequest)))
                .willReturn(List.of(CORRECT_CHALLENGE_ATTEMPT, WRONG_CHALLENGE_ATTEMPT));

        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findByUserAlias(USER.getAlias(), pageRequest);

        // then
        ChallengeAttempt firstFoundChallengeAttempt = foundChallengeAttempts.get(0);
        ChallengeAttempt secondFoundChallengeAttempt = foundChallengeAttempts.get(1);
        then(firstFoundChallengeAttempt.getTimestamp()).isGreaterThan(secondFoundChallengeAttempt.getTimestamp());
    }

}