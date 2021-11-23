package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.repository.UserRepository;
import learn.microservices.multiplicator.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final User USER_1 = new User("111", "test_1");
    private final User USER_2 = new User("222", "test_2");

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void whenCreateUser_thenReturnsCorrectEntity() {
        // given
        given(userRepository.save(any())).will(returnsFirstArg());
        // when
        User createdUser = userService.create(USER_1);
        // then
        then(createdUser).isEqualTo(USER_1);
    }

    @Test
    public void whenCreateUserFromChallengeAttemptDtoWithNoExistingAlias_thenCreatesEntity() {
        // given
        User tmpUser = new User("333", "non_existing");
        given(userRepository.findByAlias(anyString())).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(tmpUser);
        // when
        User createdUser = userService.create(new ChallengeAttemptDto(20, 30, tmpUser.getAlias(), 600));
        // then
        then(createdUser).isEqualTo(tmpUser);
    }

    @Test
    public void whenCreateUserFromChallengeAttemptDtoWithExistingAlias_thenReturnsCorrectEntity() {
        // given
        given(userRepository.findByAlias(anyString())).willReturn(Optional.ofNullable(USER_1));
        // when
        User createdUser = userService.create(new ChallengeAttemptDto(20, 30, USER_1.getAlias(), 600));
        // then
        then(createdUser).isEqualTo(USER_1);
    }

    @Test
    public void whenFindAllUsers_thenReturnedArraySizeIsCorrect() {
        // given
        given(userRepository.findAll()).willReturn(List.of(USER_1, USER_2));
        // when
        List<User> foundUsers = userService.findAll();
        // then
        then(foundUsers.size()).isEqualTo(2);
    }

    @Test
    public void whenFindUserById_thenReturnsCorrectEntity() {
        // given
        given(userRepository.findById(anyString())).willReturn(Optional.ofNullable(USER_1));
        // when
        Optional<User> foundUser = userService.findById(USER_1.getId());
        // then
        then(foundUser.get()).isEqualTo(USER_1);
    }

    @Test
    public void whenFindUserByAlias_thenReturnsCorrectEntity() {
        // given
        given(userRepository.findByAlias(anyString())).willReturn(Optional.ofNullable(USER_1));
        // when
        Optional<User> foundUser = userService.findByAlias(USER_1.getAlias());
        // then
        then(foundUser.get()).isEqualTo(USER_1);
    }

}