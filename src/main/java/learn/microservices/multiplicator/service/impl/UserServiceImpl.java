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
        User user;
        List<User> existingUsers = findByAlias(dto.getUserAlias());
        if (existingUsers != null && !existingUsers.isEmpty()) {
            user = existingUsers.get(0);
        } else {
            user = create(new User(dto.getUserAlias()));
        }
        return user;
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
    public List<User> findByAlias(String alias) {
        return repository.findAllByAlias(alias);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

}