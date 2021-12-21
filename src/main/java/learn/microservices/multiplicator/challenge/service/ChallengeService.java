package learn.microservices.multiplicator.challenge.service;

import learn.microservices.multiplicator.challenge.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChallengeService {

    /**
     * Verifies correctness of the user's guess.
     * @return the resulting ChallengeAttempt object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDto resultAttempt);

    ChallengeAttempt create(ChallengeAttempt challengeAttempt);

    List<ChallengeAttempt> findAll();

    Optional<ChallengeAttempt> findById(String id);

    List<ChallengeAttempt> findByUserId(String userId);

    List<ChallengeAttempt> findByUserAlias(String userAlias, Pageable pageable);

    void delete(ChallengeAttempt user);

}