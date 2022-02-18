package learn.microservices.multiplicator.user.repository;

import learn.microservices.multiplicator.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        User user = new User("test_user_111111");
        user1 = userRepository.save(user);

        user = new User("test_user_222222");
        user2 = userRepository.save(user);
    }

    @Test
    public void whenFindAllUsers_thenFoundSizeIsCorrect() {
        // when
        List<User> foundUsers = userRepository.findAll();

        // then
        then(foundUsers.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void whenFindUserById_thenFoundIsCorrect() {
        // given
        String id = user1.getId();

        // when
        Optional<User> foundUser = userRepository.findById(id);

        // then
        then(foundUser.get()).isEqualTo(user1);
    }

    @Test
    public void whenFindUserByAlias_thenFoundIsCorrect() {
        // given
        String alias = user1.getAlias();

        // when
        Optional<User> foundUser = userRepository.findByAlias(alias);

        // then
        then(foundUser.get()).isEqualTo(user1);
    }

    @Test
    public void whenFindAllUsersByIds_thenFoundUsersAreCorrect() {
        // given
        List<String> ids = List.of(user1.getId(), user2.getId());

        // when
        List<User> foundUsers = userRepository.findAllByIdIn(ids);

        // then
        then(foundUsers.size()).isEqualTo(2);
        then(foundUsers.get(0)).isEqualTo(user1);
        then(foundUsers.get(1)).isEqualTo(user2);
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(user1);
        userRepository.delete(user2);
    }

}