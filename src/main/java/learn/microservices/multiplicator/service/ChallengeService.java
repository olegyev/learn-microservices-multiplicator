package learn.microservices.multiplicator.service;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.ChallengeAttempt;

public interface ChallengeService {

    /**
     * Verifies correctness of the user's guess.
     * @return the resulting ChallengeAttempt object
     * */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDto resultAttempt);

}