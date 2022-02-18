package learn.microservices.multiplicator.challenge.publisher;

import learn.microservices.multiplicator.challenge.dto.ChallengeSolvedEvent;
import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeSolvedEventPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String challengesTopicExchangeName;

    public ChallengeSolvedEventPublisher(final AmqpTemplate amqpTemplate,
                                         @Value("${amqp.exchange.attempts}") final String challengesTopicExchangeName) {
        this.amqpTemplate = amqpTemplate;
        this.challengesTopicExchangeName = challengesTopicExchangeName;
    }

    public void sendEvent(final ChallengeAttempt challengeAttempt) {
        ChallengeSolvedEvent event = buildEvent(challengeAttempt);

        // Routing keys:
        // 'attempt.correct'
        // 'attempt.wrong'
        String routingKey = "attempt." + (event.isCorrect() ? "correct" : "wrong");

        amqpTemplate.convertAndSend(
                challengesTopicExchangeName
                , routingKey
                , event
                // Uncomment to remove queued messages on broker's restart.
                // , m -> { m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT); return m; }
        );
    }

    private ChallengeSolvedEvent buildEvent(final ChallengeAttempt challengeAttempt) {
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