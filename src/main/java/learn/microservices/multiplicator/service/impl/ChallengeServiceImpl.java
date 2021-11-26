package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.ChallengeAttempt;
import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.repository.ChallengeAttemptRepository;
import learn.microservices.multiplicator.service.ChallengeService;
import learn.microservices.multiplicator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeAttemptRepository repository;
    private final UserService userService;

    @Override
    public ChallengeAttempt verifyAttempt(final ChallengeAttemptDto dto) {
        boolean isCorrect = dto.getGuess() == (dto.getFactorA() * dto.getFactorB());
        User user = userService.findByAlias(dto.getUserAlias())
                .orElseGet(() ->
                        userService.create(new User(dto.getUserAlias()))
                );
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(
                user,
                dto.getFactorA(),
                dto.getFactorB(),
                dto.getGuess(),
                isCorrect,
                Calendar.getInstance().getTimeInMillis()
        );
        return create(checkedAttempt);
    }

    @Override
    public ChallengeAttempt create(ChallengeAttempt challengeAttempt) {
        return repository.save(challengeAttempt);
    }

    @Override
    public List<ChallengeAttempt> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ChallengeAttempt> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<ChallengeAttempt> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<ChallengeAttempt> findByUserAlias(String userAlias, Pageable pageable) {
        return repository.findAllByUserAliasOrderByTimestampDesc(userAlias, pageable);
    }

    @Override
    public void delete(ChallengeAttempt challengeAttempt) {
        repository.delete(challengeAttempt);
    }

}