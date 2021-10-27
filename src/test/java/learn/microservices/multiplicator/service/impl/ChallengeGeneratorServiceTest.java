package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.entity.Challenge;
import learn.microservices.multiplicator.service.ChallengeGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ChallengeGeneratorServiceTest {

    private ChallengeGeneratorService challengeGeneratorService;

    @Spy
    private Random random;

    @BeforeEach
    public void setUp() {
        challengeGeneratorService = new ChallengeGeneratorServiceImpl(random);
    }

    @Test
    public void generatedRandomFactorIsBetweenExpectedLimits() {
        // given
        given(random.nextInt(89)).willReturn(20, 30);
        // when
        Challenge challenge = challengeGeneratorService.generateRandomChallenge();
        // then - need to add 11 to generate random numbers from 11 to 99
        then(challenge).isEqualTo(new Challenge(31, 41));
    }

}