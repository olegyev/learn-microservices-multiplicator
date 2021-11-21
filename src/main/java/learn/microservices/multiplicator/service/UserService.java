package learn.microservices.multiplicator.service;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    User create(ChallengeAttemptDto dto);

    List<User> findAll();

    Optional<User> findById(String id);

    Optional<User> findByAlias(String alias);

    void delete(User user);

}