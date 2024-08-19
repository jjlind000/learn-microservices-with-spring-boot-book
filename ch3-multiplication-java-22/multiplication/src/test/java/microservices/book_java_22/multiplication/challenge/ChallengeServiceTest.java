package microservices.book_java_22.multiplication.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class ChallengeServiceTest {
    private ChallengeService challengeService;

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl();
    }

    @Test
    public void checkCorrectAttemptTest() {
// given
        ChallengeAttemptDTOValue attemptDTO = new ChallengeAttemptDTOValue(50, 60, "john_doe", 3000);
// when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
// then
        then(resultAttempt.isCorrect()).isTrue();
    }


    @Test
    public void checkWrongAttemptTest() {
// given
        ChallengeAttemptDTOValue attemptDTO = new ChallengeAttemptDTOValue(50, 60, "john_doe", 5000);
// when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
// then
        then(resultAttempt.isCorrect()).isFalse();
    }
}