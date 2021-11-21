package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.ChallengeAttempt;
import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.service.ChallengeService;
import learn.microservices.multiplicator.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
public class ChallengeServiceTest {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserService userService;

    @Value("${test.user.alias}")
    private String userAlias;

    private final List<ChallengeAttempt> createdChallengeAttempts = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        User user = userService.create(new User(userAlias));

        ChallengeAttempt preparedFirst = initChallengeAttempt(user);

        try {
            // to create challenge attempts with time gap to test fetching order by timestamp
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore
        }

        ChallengeAttempt preparedSecond = initChallengeAttempt(user);

        ChallengeAttempt createdFirst = challengeService.create(preparedFirst);
        ChallengeAttempt createdSecond = challengeService.create(preparedSecond);

        createdChallengeAttempts.add(createdFirst);
        createdChallengeAttempts.add(createdSecond);
    }

    @Test
    public void whenCorrectChallengeAttempt_thenIsCorrectTrue() {
        // given
        String userAlias = createdChallengeAttempts.get(0).getUser().getAlias();
        ChallengeAttemptDto dto = new ChallengeAttemptDto(20, 30, userAlias, 600);
        // when
        ChallengeAttempt result = challengeService.verifyAttempt(dto);
        // then
        then(result.isCorrect()).isTrue();
    }

    @Test
    public void whenWrongChallengeAttempt_thenIsCorrectFalse() {
        // given
        String userAlias = createdChallengeAttempts.get(0).getUser().getAlias();
        ChallengeAttemptDto dto = new ChallengeAttemptDto(20, 30, userAlias, 500);
        // when
        ChallengeAttempt result = challengeService.verifyAttempt(dto);
        // then
        then(result.isCorrect()).isFalse();
    }

    @Test
    public void whenFindAll_thenFoundSizeIsCorrect() {
        // given
        int numberOfTestingChallengeAttempts = createdChallengeAttempts.size();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findAll();
        // then
        then(foundChallengeAttempts.size()).isGreaterThanOrEqualTo(numberOfTestingChallengeAttempts);
    }

    @Test
    public void whenFindById_thenCorrectChallengeAttemptReturned() {
        // given
        ChallengeAttempt firstTestChallengeAttempt = createdChallengeAttempts.get(0);
        String id = firstTestChallengeAttempt.getId();
        // when
        Optional<ChallengeAttempt> foundChallengeAttempt = challengeService.findById(id);
        // then
        then(foundChallengeAttempt.get()).isEqualTo(firstTestChallengeAttempt);
    }

    @Test
    public void whenFindByUserId_thenCorrectChallengeAttemptsSize() {
        // given
        String userId = createdChallengeAttempts.get(0).getUser().getId();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findByUserId(userId);
        // then
        then(foundChallengeAttempts.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void whenFindByUserAlias_thenCorrectChallengeAttemptsSize() {
        // given
        String userAlias = createdChallengeAttempts.get(0).getUser().getAlias();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findByUserAlias(userAlias);
        // then
        then(foundChallengeAttempts.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void whenFindByUserAlias_thenTimestampOrderDesc() {
        // given
        String userAlias = createdChallengeAttempts.get(0).getUser().getAlias();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findByUserAlias(userAlias);
        // then
        ChallengeAttempt firstFoundChallengeAttempt = foundChallengeAttempts.get(0);
        ChallengeAttempt secondFoundChallengeAttempt = foundChallengeAttempts.get(1);
        then(firstFoundChallengeAttempt.getTimestamp()).isGreaterThan(secondFoundChallengeAttempt.getTimestamp());
    }

    @AfterEach
    public void deleteObject() {
        userService.delete(createdChallengeAttempts.get(0).getUser());
        createdChallengeAttempts.forEach(attempt -> challengeService.delete(attempt));
    }

    private ChallengeAttempt initChallengeAttempt(User user) {
        return new ChallengeAttempt(
                user,
                20,
                30,
                600,
                true,
                Calendar.getInstance().getTimeInMillis()
        );
    }

}