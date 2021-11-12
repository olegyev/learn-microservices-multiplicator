package learn.microservices.multiplicator.repository;

import learn.microservices.multiplicator.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Long> {

    @Query("{alias: '?0'}")
    public List<User> findAllByAlias(String alias);

}