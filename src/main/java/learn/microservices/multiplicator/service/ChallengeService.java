package learn.microservices.multiplicator.service;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.ChallengeAttempt;

import java.util.List;
import java.util.Optional;

public interface ChallengeService {

    /**
     * Verifies correctness of the user's guess.
     *
     * @return the resulting ChallengeAttempt object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDto resultAttempt);

    ChallengeAttempt create(ChallengeAttempt challengeAttempt);

    List<ChallengeAttempt> findAll();

    Optional<ChallengeAttempt> findById(String id);

    List<ChallengeAttempt> findByUserId(String userId);

    void delete(ChallengeAttempt user);

}