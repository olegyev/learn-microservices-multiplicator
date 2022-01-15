package learn.microservices.multiplicator.challenge.controller;

import learn.microservices.multiplicator.challenge.entity.Challenge;
import learn.microservices.multiplicator.challenge.service.ChallengeGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeGeneratorService challengeGeneratorService;

    @GetMapping("/random")
    public ResponseEntity<Challenge> getRandomChallenge() {
        Challenge challenge = challengeGeneratorService.generateRandomChallenge();
        return new ResponseEntity(challenge, HttpStatus.CREATED);
    }

}