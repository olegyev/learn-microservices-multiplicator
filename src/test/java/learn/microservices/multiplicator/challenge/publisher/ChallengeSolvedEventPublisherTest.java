package learn.microservices.multiplicator.challenge.publisher;

import learn.microservices.multiplicator.challenge.dto.ChallengeSolvedEvent;
import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import learn.microservices.multiplicator.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeSolvedEventPublisherTest {

    private ChallengeSolvedEventPublisher challengeSolvedEventPublisher;

    @Mock
    private AmqpTemplate amqpTemplate;

    @BeforeEach
    public void setUp() {
        challengeSolvedEventPublisher = new ChallengeSolvedEventPublisher(amqpTemplate, "test.topic");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void whenSendEventCorrectAndWrong_thenSentSuccessfully(boolean isCorrect) {
        // given
        ChallengeAttempt challengeAttempt = createTestAttempt(isCorrect);

        // when
        challengeSolvedEventPublisher.sendEvent(challengeAttempt);

        // then
        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(ChallengeSolvedEvent.class);

        verify(amqpTemplate).convertAndSend(
                exchangeCaptor.capture(),
                routingKeyCaptor.capture(),
                eventCaptor.capture()
        );

        then(exchangeCaptor.getValue()).isEqualTo("test.topic");
        then(routingKeyCaptor.getValue()).isEqualTo("attempt." + (isCorrect ? "correct" : "wrong"));
        then(eventCaptor.getValue()).isEqualTo(createTestChallengeSolvedEvent(challengeAttempt));
    }

    private ChallengeAttempt createTestAttempt(boolean isCorrect) {
        return new ChallengeAttempt(
                "1",
                new User("1", "john_doe"),
                30,
                40,
                isCorrect ? 1200 : 1300,
                isCorrect,
                System.currentTimeMillis());
    }

    private ChallengeSolvedEvent createTestChallengeSolvedEvent(ChallengeAttempt challengeAttempt) {
        return new ChallengeSolvedEvent(
                challengeAttempt.getId(),
                challengeAttempt.isCorrect(),
                challengeAttempt.getFactorA(),
                challengeAttempt.getFactorB(),
                challengeAttempt.getUser().getId(),
                challengeAttempt.getUser().getAlias()
        );
    }

}