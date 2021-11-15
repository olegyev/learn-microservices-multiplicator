package learn.microservices.multiplicator.service;

import learn.microservices.multiplicator.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    List<User> findAll();

    Optional<User> findById(String id);

    List<User> findByAlias(String alias);

    void delete(User user);

}