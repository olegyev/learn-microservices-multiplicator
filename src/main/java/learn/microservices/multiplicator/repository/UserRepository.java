package learn.microservices.multiplicator.repository;

import learn.microservices.multiplicator.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{alias: '?0'}")
    Optional<User> findByAlias(String alias);

}