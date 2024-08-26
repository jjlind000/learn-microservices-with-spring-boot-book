package microservices.book_java_22.multiplication.challenge;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class provides a REST API to POST the attempts from users.
 */
@Slf4j
@RequiredArgsConstructor // lombok adds a constructor and Spring will inject the corresponding bean ChallengeServiceImpl.
@RestController
@RequestMapping("/attempts")
class ChallengeAttemptController {
    private final ChallengeService challengeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ChallengeAttempt> postResult(@RequestBody @Valid ChallengeAttemptRequest challengeAttemptRequest) {;
        return ResponseEntity.ok(challengeService.verifyAttempt(challengeAttemptRequest));
    }

   @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
   ResponseEntity<List<ChallengeAttempt>> getAttempts(@RequestParam String userAlias) {
        List<ChallengeAttempt> lastAttempts = challengeService.getLastAttempts(userAlias);
        return ResponseEntity.ok(lastAttempts);
    }

    @GetMapping("/stats")
    ResponseEntity<List<ChallengeAttempt>> getStatistics(@RequestParam("userAlias") String alias) {
        List<ChallengeAttempt> statsForUser = challengeService.getStatsForUser(alias);
        log.info("got {}", statsForUser.toString());
        return ResponseEntity.ok(statsForUser);
    }
}
