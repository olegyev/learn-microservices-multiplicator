package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    private User createdUser;

    @Before
    public void setUp() {
        User user = new User("john_doe");
        createdUser = userService.create(user);
    }

    @Test
    public void allUsersFoundSizeIsCorrect() {
        // given createdUser
        // when
        List<User> foundUsers = userService.findAll();
        // then
        then(foundUsers.size()).isEqualTo(1);
    }

    @Test
    public void allUsersFoundContentIsCorrect() {
        // given createdUser
        // when
        List<User> foundUsers = userService.findAll();
        // then
        then(foundUsers.get(0)).isEqualTo(createdUser);
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
        List<User> foundUsers = userService.findByAlias(alias);
        // then
        then(foundUsers.size()).isEqualTo(1);
    }

    @Test
    public void usersFoundByAliasContentIsCorrect() {
        // given
        String alias = createdUser.getAlias();
        // when
        List<User> foundUsers = userService.findByAlias(alias);
        // then
        then(foundUsers.get(0)).isEqualTo(createdUser);
    }

    @After
    public void deleteObject() {
        userService.delete(createdUser);
    }

}