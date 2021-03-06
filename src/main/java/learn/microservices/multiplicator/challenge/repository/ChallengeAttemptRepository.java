package learn.microservices.multiplicator.challenge.repository;

import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChallengeAttemptRepository extends MongoRepository<ChallengeAttempt, String> {

    List<ChallengeAttempt> findByUserId(String userId);

    List<ChallengeAttempt> findAllByUserAliasOrderByTimestampDesc(String userAlias, Pageable pageable);

}