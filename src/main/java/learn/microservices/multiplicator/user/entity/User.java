package learn.microservices.multiplicator.user.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document("users")
public class User {

    @Id
    private String id;

    @NonNull
    @Indexed(sparse = true)
    private String alias;

}