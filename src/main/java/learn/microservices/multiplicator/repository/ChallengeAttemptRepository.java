package learn.microservices.multiplicator.repository;

import learn.microservices.multiplicator.entity.ChallengeAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChallengeAttemptRepository extends MongoRepository<ChallengeAttempt, String> {

    List<ChallengeAttempt> findByUserId(String userId);

    List<ChallengeAttempt> findAllByUserAliasOrderByTimestampDesc(String userAlias);

}