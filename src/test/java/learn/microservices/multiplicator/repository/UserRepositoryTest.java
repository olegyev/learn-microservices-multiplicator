package learn.microservices.multiplicator.repository;

import learn.microservices.multiplicator.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.BDDAssertions.then;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    public void checkUserCreated() {
        // given
        User user = new User("john_doe");
        // when
        User createdUser = repository.save(user);
        // then
        then(createdUser.getAlias()).isEqualTo(user.getAlias());
    }

}