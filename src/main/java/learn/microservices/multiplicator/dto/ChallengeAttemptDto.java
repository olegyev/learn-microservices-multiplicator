package learn.microservices.multiplicator.dto;

import lombok.Value;

@Value
public class ChallengeAttemptDto {

    int factorA, factorB;
    String userAlias;
    int guess;

}