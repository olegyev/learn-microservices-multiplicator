package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.service.UserService;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private User createdUser;

    @Value("${test.user.alias}")
    private String userAlias;

    @BeforeEach
    public void setUp() {
        User user = new User(userAlias);
        createdUser = userService.create(user);
    }

    @Test
    public void allUsersFoundSizeIsCorrect() {
        // given createdUser
        // when
        List<User> foundUsers = userService.findAll();
        // then
        then(foundUsers.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void userFoundById() {
        // given
        String id = createdUser.getId();
        // when
        Optional<User> foundUser = userService.findById(id);
        // then
        then(foundUser.get()).isEqualTo(createdUser);
    }

    @Test
    public void usersFoundByAliasSizeIsCorrect() {
        // given
        String alias = createdUser.getAlias();
        // when
        Optional<User> foundUser = userService.findByAlias(alias);
        // then
        then(foundUser.get()).isEqualTo(createdUser);
    }

    @AfterEach
    public void deleteObject() {
        userService.delete(createdUser);
    }

}