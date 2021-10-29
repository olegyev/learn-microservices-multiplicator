package learn.microservices.multiplicator.dto;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * Coming from the client
 */
@Value
public class ChallengeAttemptDto {

    @Min(1) @Max(99)
    int factorA, factorB;

    @NotBlank
    String userAlias;

    @Positive
    int guess;

}