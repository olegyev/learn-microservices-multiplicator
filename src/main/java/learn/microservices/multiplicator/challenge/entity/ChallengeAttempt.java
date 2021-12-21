package learn.microservices.multiplicator.challenge.entity;

import learn.microservices.multiplicator.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @NonNull
    private long timestamp;

}