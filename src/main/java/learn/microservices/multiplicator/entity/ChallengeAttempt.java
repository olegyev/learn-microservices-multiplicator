package learn.microservices.multiplicator.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ChallengeAttempt {

    private Long id;
    private User user;
    private int factorA;
    private int factorB;
    private int resultAttempt;
    private boolean isCorrect;

}