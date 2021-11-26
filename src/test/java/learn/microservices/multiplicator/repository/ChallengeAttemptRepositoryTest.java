package learn.microservices.multiplicator.repository;

import learn.microservices.multiplicator.entity.ChallengeAttempt;
import learn.microservices.multiplicator.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
public class ChallengeAttemptRepositoryTest {

    @Autowired
    private ChallengeAttemptRepository challengeAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${test.user.alias}")
    private String userAlias;

    private final List<ChallengeAttempt> createdChallengeAttempts = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        User user = userRepository.save(new User(userAlias));

        ChallengeAttempt preparedFirst = initChallengeAttempt(user);

        try {
            // to create challenge attempts with time gap to test fetching order by timestamp
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore
        }

        ChallengeAttempt preparedSecond = initChallengeAttempt(user);

        ChallengeAttempt createdFirst = challengeAttemptRepository.save(preparedFirst);
        ChallengeAttempt createdSecond = challengeAttemptRepository.save(preparedSecond);

        createdChallengeAttempts.add(createdFirst);
        createdChallengeAttempts.add(createdSecond);
    }

    @Test
    public void whenFindAll_thenFoundSizeIsCorrect() {
        // given
        int numberOfTestingChallengeAttempts = createdChallengeAttempts.size();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeAttemptRepository.findAll();
        // then
        then(foundChallengeAttempts.size()).isGreaterThanOrEqualTo(numberOfTestingChallengeAttempts);
    }

    @Test
    public void whenFindById_thenFoundIsCorrect() {
        // given
        ChallengeAttempt firstTestChallengeAttempt = createdChallengeAttempts.get(0);
        String id = firstTestChallengeAttempt.getId();
        // when
        Optional<ChallengeAttempt> foundChallengeAttempt = challengeAttemptRepository.findById(id);
        // then
        then(foundChallengeAttempt.get()).isEqualTo(firstTestChallengeAttempt);
    }

    @Test
    public void whenFindByUserId_thenFoundSizeIsCorrect() {
        // given
        String userId = createdChallengeAttempts.get(0).getUser().getId();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeAttemptRepository.findByUserId(userId);
        // then
        then(foundChallengeAttempts.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void whenFindByUserAlias_thenFoundSizeIsCorrect() {
        // given
        String userAlias = createdChallengeAttempts.get(0).getUser().getAlias();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeAttemptRepository.findAllByUserAliasOrderByTimestampDesc(userAlias);
        // then
        then(foundChallengeAttempts.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void whenFindByUserAlias_thenTimestampOrderDesc() {
        // given
        String userAlias = createdChallengeAttempts.get(0).getUser().getAlias();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeAttemptRepository.findAllByUserAliasOrderByTimestampDesc(userAlias);
        // then
        ChallengeAttempt firstFoundChallengeAttempt = foundChallengeAttempts.get(0);
        ChallengeAttempt secondFoundChallengeAttempt = foundChallengeAttempts.get(1);
        then(firstFoundChallengeAttempt.getTimestamp()).isGreaterThan(secondFoundChallengeAttempt.getTimestamp());
    }

    @AfterEach
    public void deleteObject() {
        userRepository.delete(createdChallengeAttempts.get(0).getUser());
        createdChallengeAttempts.forEach(attempt -> challengeAttemptRepository.delete(attempt));
    }

    private ChallengeAttempt initChallengeAttempt(User user) {
        return new ChallengeAttempt(
                user,
                20,
                30,
                600,
                true,
                System.currentTimeMillis()
        );
    }

}