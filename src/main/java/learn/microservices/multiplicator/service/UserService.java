package learn.microservices.multiplicator.service;

import learn.microservices.multiplicator.entity.User;

import java.util.List;

public interface UserService {

    User create(User user);

    List<User> findAll();

    User findById(Long id);

    List<User> findByAlias(String alias);

}