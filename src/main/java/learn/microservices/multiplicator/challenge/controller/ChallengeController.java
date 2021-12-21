package learn.microservices.multiplicator.challenge.controller;

import learn.microservices.multiplicator.challenge.entity.Challenge;
import learn.microservices.multiplicator.challenge.service.ChallengeGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
@Slf4j
public class ChallengeController {

    private final ChallengeGeneratorService challengeGeneratorService;

    @GetMapping("/random")
    public Challenge getRandomChallenge() {
        Challenge challenge = challengeGeneratorService.generateRandomChallenge();
        log.info("New challenge generated: {}", challenge);
        return challenge;
    }

}