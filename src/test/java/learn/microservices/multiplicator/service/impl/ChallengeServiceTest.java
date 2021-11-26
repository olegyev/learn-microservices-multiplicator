package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.ChallengeAttempt;
import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.repository.ChallengeAttemptRepository;
import learn.microservices.multiplicator.service.ChallengeService;
import learn.microservices.multiplicator.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {

    private ChallengeService challengeService;

    @Mock
    private ChallengeAttemptRepository challengeAttemptRepository;

    @Mock
    private UserService userService;

    private final User USER = new User("111", "test_1");
    private final ChallengeAttempt CORRECT_CHALLENGE_ATTEMPT = new ChallengeAttempt(
            "111",
            USER,
            20,
            30,
            600,
            true,
            System.currentTimeMillis() + 10
    );
    private final ChallengeAttempt WRONG_CHALLENGE_ATTEMPT = new ChallengeAttempt(
            "222",
            USER,
            20,
            30,
            500,
            false,
            System.currentTimeMillis()
    );

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(challengeAttemptRepository, userService);
    }

    @Test
    public void whenCorrectChallengeAttempt_thenIsCorrectTrue() {
        // given
        ChallengeAttemptDto dto = new ChallengeAttemptDto(20, 30, "john_doe", 600);
        given(userService.create(any())).will(returnsFirstArg());
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
        // when
        ChallengeAttempt result = challengeService.verifyAttempt(dto);
        // then
        then(result.isCorrect()).isTrue();
        verify(userService).create(new User("john_doe"));
        verify(challengeAttemptRepository).save(result);
    }

    @Test
    public void whenWrongChallengeAttempt_thenIsCorrectFalse() {
        // given
        /*ChallengeAttemptDto dto = new ChallengeAttemptDto(20, 30, USER.getAlias(), 500);
        given(userService.create((ChallengeAttemptDto) any())).willReturn(USER);
        // when
        ChallengeAttempt result = challengeService.verifyAttempt(dto);
        // then
        then(result.isCorrect()).isFalse();*/
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
        given(challengeAttemptRepository.findAllByUserAliasOrderByTimestampDesc(anyString()))
                .willReturn(List.of(CORRECT_CHALLENGE_ATTEMPT, WRONG_CHALLENGE_ATTEMPT));
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findByUserAlias(USER.getAlias());
        // then
        then(foundChallengeAttempts.size()).isEqualTo(2);
    }

    @Test
    public void whenFindByUserAlias_thenTimestampOrderDesc() {
        // given
        given(challengeAttemptRepository.findAllByUserAliasOrderByTimestampDesc(anyString()))
                .willReturn(List.of(CORRECT_CHALLENGE_ATTEMPT, WRONG_CHALLENGE_ATTEMPT));
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findByUserAlias(USER.getAlias());
        // then
        ChallengeAttempt firstFoundChallengeAttempt = foundChallengeAttempts.get(0);
        ChallengeAttempt secondFoundChallengeAttempt = foundChallengeAttempts.get(1);
        then(firstFoundChallengeAttempt.getTimestamp()).isGreaterThan(secondFoundChallengeAttempt.getTimestamp());
    }

}