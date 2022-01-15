package learn.microservices.multiplicator.serviceclient;

import learn.microservices.multiplicator.challenge.dto.ChallengeSolvedDto;
import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GamificationServiceClient {

    private final RestTemplate restTemplate;
    private final String gamificationHostUrl;

    public GamificationServiceClient(final RestTemplateBuilder builder,
                                     @Value("${service.gamification.host}")
                                             String gamificationHostUrl) {
        restTemplate = builder.build();
        this.gamificationHostUrl = gamificationHostUrl;
    }

    public boolean sendAttempt(final ChallengeAttempt attempt) {
        try {
            ChallengeSolvedDto dto = new ChallengeSolvedDto(
                    attempt.getId(),
                    attempt.isCorrect(),
                    attempt.getFactorA(),
                    attempt.getFactorB(),
                    attempt.getUser().getId(),
                    attempt.getUser().getAlias()
            );
            ResponseEntity<String> response = restTemplate.postForEntity(
                    gamificationHostUrl + "/attempts",
                    dto,
                    String.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

}