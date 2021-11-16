package learn.microservices.multiplicator.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Document("challenge_attempts")
public class ChallengeAttempt {

    @Id
    private String id;

    @NonNull
    private User user;

    @NonNull
    private int factorA;

    @NonNull
    private int factorB;

    @NonNull
    private int resultAttempt;

    @NonNull
    private boolean isCorrect;

}