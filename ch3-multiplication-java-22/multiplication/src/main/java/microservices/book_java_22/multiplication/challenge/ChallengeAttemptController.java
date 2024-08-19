package microservices.book_java_22.multiplication.challenge;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    ResponseEntity<ChallengeAttempt> postResult(@RequestBody @Valid ChallengeAttemptDTOValue challengeAttemptDTO) {
        return ResponseEntity.ok(challengeService.verifyAttempt(
                challengeAttemptDTO));
    }

//    @PostMapping(value="/hellopost", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public XX post(@RequestBody XX x) {
//        log.info("got {}", x);
//        return x;
//    }

}
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//class XX {
//    String message;
//    String factorA;
//}