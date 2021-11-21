package learn.microservices.multiplicator.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Document("users")
public class User {

    @Id
    private String id;

    @NonNull
    @Indexed
    private String alias;

}