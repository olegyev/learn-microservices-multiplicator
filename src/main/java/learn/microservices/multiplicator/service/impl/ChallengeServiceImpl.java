package learn.microservices.multiplicator.service.impl;

import learn.microservices.multiplicator.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.entity.ChallengeAttempt;
import learn.microservices.multiplicator.entity.User;
import learn.microservices.multiplicator.service.ChallengeService;
import org.springframework.stereotype.Service;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Override
    public ChallengeAttempt verifyAttempt(final ChallengeAttemptDto dto) {
        boolean isCorrect = dto.getGuess() == (dto.getFactorA() * dto.getFactorB());
        User user = new User(null, dto.getUserAlias());
        return new ChallengeAttempt(
                null,
                user.getId(),
                dto.getFactorA(),
                dto.getFactorB(),
                dto.getGuess(),
                isCorrect
        );
    }

}