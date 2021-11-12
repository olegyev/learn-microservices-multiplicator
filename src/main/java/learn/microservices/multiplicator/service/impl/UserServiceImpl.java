package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.repository.UserRepository;
import learn.microservices.multiplicator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findByAlias(String alias) {
        return null;
    }

}