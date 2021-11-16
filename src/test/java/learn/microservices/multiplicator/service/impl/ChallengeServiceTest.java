package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.ChallengeAttempt;
import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.service.ChallengeService;
import learn.microservices.multiplicator.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
public class ChallengeServiceTest {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserService userService;

    private ChallengeAttempt createdChallengeAttempt;

    @BeforeEach
    public void setUp() {
        createdChallengeAttempt = initChallengeAttempt();
        challengeService.create(createdChallengeAttempt);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        ChallengeAttemptDto dto = new ChallengeAttemptDto(20, 30, "john_doe", 600);
        // when
        ChallengeAttempt result = challengeService.verifyAttempt(dto);
        // then
        then(result.isCorrect()).isTrue();
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        ChallengeAttemptDto dto = new ChallengeAttemptDto(20, 30, "john_doe", 500);
        // when
        ChallengeAttempt result = challengeService.verifyAttempt(dto);
        // then
        then(result.isCorrect()).isFalse();
    }

    @Test
    public void allChallengeAttemptsFoundSizeIsCorrect() {
        // given createdChallengeAttempt
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findAll();
        // then
        then(foundChallengeAttempts.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void challengeAttemptFoundById() {
        // given
        String id = createdChallengeAttempt.getId();
        // when
        Optional<ChallengeAttempt> foundChallengeAttempt = challengeService.findById(id);
        // then
        then(foundChallengeAttempt.get()).isEqualTo(createdChallengeAttempt);
    }

    @Test
    public void challengeAttemptsFoundByUserIdSizeIsCorrect() {
        // given
        String userId = createdChallengeAttempt.getUser().getId();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeService.findByUserId(userId);
        // then
        then(foundChallengeAttempts.size()).isGreaterThanOrEqualTo(1);
    }

    @AfterEach
    public void deleteObject() {
        userService.delete(createdChallengeAttempt.getUser());
        challengeService.delete(createdChallengeAttempt);
    }

    private ChallengeAttempt initChallengeAttempt() {
        User user = userService.create(new User("john_doe"));
        return new ChallengeAttempt(
                user,
                20,
                30,
                600,
                true
        );
    }

}