package learn.microservices.multiplicator.challenge.repository;

import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import learn.microservices.multiplicator.user.entity.User;
import learn.microservices.multiplicator.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.same;

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
        PageRequest pageRequest = PageRequest.of(0, 2);
        String userAlias = createdChallengeAttempts.get(0).getUser().getAlias();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeAttemptRepository.findAllByUserAliasOrderByTimestampDesc(userAlias, same(pageRequest));
        // then
        then(foundChallengeAttempts.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void whenFindByUserAlias_thenTimestampOrderDesc() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 2);
        String userAlias = createdChallengeAttempts.get(0).getUser().getAlias();
        // when
        List<ChallengeAttempt> foundChallengeAttempts = challengeAttemptRepository.findAllByUserAliasOrderByTimestampDesc(userAlias, same(pageRequest));
        // then
        ChallengeAttempt firstFoundChallengeAttempt = foundChallengeAttempts.get(0);
        ChallengeAttempt secondFoundChallengeAttempt = foundChallengeAttempts.get(1);
        then(firstFoundChallengeAttempt.getTimestamp()).isGreaterThan(secondFoundChallengeAttempt.getTimestamp());
    }

    @AfterEach
    public void tearDown() {
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