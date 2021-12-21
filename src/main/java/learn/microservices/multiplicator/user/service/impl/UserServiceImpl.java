package learn.microservices.multiplicator.user.service.impl;

import learn.microservices.multiplicator.user.entity.User;
import learn.microservices.multiplicator.user.repository.UserRepository;
import learn.microservices.multiplicator.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByAlias(String alias) {
        return repository.findByAlias(alias);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

}