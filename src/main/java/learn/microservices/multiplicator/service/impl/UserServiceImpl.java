package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.repository.UserRepository;
import learn.microservices.multiplicator.service.UserService;
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
    public User create(ChallengeAttemptDto dto) {
        Optional<User> existingUser = findByAlias(dto.getUserAlias());
        return existingUser.orElseGet(() -> create(new User(dto.getUserAlias())));
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