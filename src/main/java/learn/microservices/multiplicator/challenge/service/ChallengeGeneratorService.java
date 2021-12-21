package learn.microservices.multiplicator.challenge.service;

import learn.microservices.multiplicator.challenge.entity.Challenge;

public interface ChallengeGeneratorService {

    /**
     * @return a randomly generated multiplication challenge with factors between 11 and 99
     */
    Challenge generateRandomChallenge();

}