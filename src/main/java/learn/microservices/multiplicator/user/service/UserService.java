package learn.microservices.multiplicator.user.service;

import learn.microservices.multiplicator.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    List<User> findAll();

    List<User> findAllByIdIn(List<String> idList);

    Optional<User> findById(String id);

    Optional<User> findByAlias(String alias);

    void delete(User user);

}