package learn.microservices.multiplicator;

import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User createdUser;

    @Value("${test.user.alias}")
    private String userAlias;

    @BeforeEach
    public void setUp() {
        User user = new User(userAlias);
        createdUser = userRepository.save(user);
    }

    @Test
    public void whenFindAllUsers_thenFoundUsersListSizeIsCorrect() {
        // when
        List<User> foundUsers = userRepository.findAll();
        // then
        then(foundUsers.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void whenFindUserById_thenFoundUserIsCorrect() {
        // given
        String id = createdUser.getId();
        // when
        Optional<User> foundUser = userRepository.findById(id);
        // then
        then(foundUser.get()).isEqualTo(createdUser);
    }

    @Test
    public void whenFindUserByAlias_thenFoundUserIsCorrect() {
        // given
        String alias = createdUser.getAlias();
        // when
        Optional<User> foundUser = userRepository.findByAlias(alias);
        // then
        then(foundUser.get()).isEqualTo(createdUser);
    }

    @AfterEach
    public void deleteObject() {
        userRepository.delete(createdUser);
    }

}