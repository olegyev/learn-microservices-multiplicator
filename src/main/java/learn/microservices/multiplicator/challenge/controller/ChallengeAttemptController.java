package learn.microservices.multiplicator.challenge.controller;

import learn.microservices.multiplicator.challenge.dto.ChallengeAttemptDto;
import learn.microservices.multiplicator.challenge.entity.ChallengeAttempt;
import learn.microservices.multiplicator.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
@Slf4j
public class ChallengeAttemptController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ChallengeAttempt> postResult(@RequestBody @Valid ChallengeAttemptDto dto) {
        return ResponseEntity.ok(challengeService.verifyAttempt(dto));
    }

    @GetMapping
    public ResponseEntity<List<ChallengeAttempt>> getAllByAlias(@RequestParam String alias,
                                                                @PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable defaultPageable) {
        return ResponseEntity.ok(challengeService.findByUserAlias(alias, defaultPageable));
    }

}