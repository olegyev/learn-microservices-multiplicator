package learn.microservices.multiplicator.challenge.service.impl;

import learn.microservices.multiplicator.challenge.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import learn.microservices.multiplicator.challenge.publisher.ChallengeSolvedEventPublisher;
import learn.microservices.multiplicator.challenge.repository.ChallengeAttemptRepository;
import learn.microservices.multiplicator.challenge.service.ChallengeService;
import learn.microservices.multiplicator.user.entity.User;
import learn.microservices.multiplicator.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeAttemptRepository repository;
    private final UserService userService;
    private final ChallengeSolvedEventPublisher challengeSolvedEventPublisher;

    @Transactional // rollback DB query if something goes wrong (e.g. RabbitMQ broker is unavailable)
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

        // Stores the attempt in DB.
        ChallengeAttempt createdAttempt = create(checkedAttempt);

        // Publishes an event for the potentially interested subscribers.
        // We don’t need transactionality over the message broker’s operation, since this is the last method's operation which ->
        // message will not be sent anyway in case of any exception within this method.
        challengeSolvedEventPublisher.sendEvent(createdAttempt);

        log.info("Attempt stored: {}", createdAttempt);

        return createdAttempt;
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