package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.ChallengeAttempt;
import learn.microservices.multiplicator.service.ChallengeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class ChallengeServiceTest {

    private ChallengeService challengeService;

    @BeforeEach
    public void setUp() {
        this.challengeService = new ChallengeServiceImpl();
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

}