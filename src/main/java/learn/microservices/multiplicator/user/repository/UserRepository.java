package learn.microservices.multiplicator.user.repository;

import learn.microservices.multiplicator.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{alias: '?0'}")
    Optional<User> findByAlias(String alias);

    List<User> findAllByIdIn(List<String> ids);

}