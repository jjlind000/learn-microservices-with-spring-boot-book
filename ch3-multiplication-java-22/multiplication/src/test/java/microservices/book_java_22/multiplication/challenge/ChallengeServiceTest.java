package microservices.book_java_22.multiplication.challenge;

import microservices.book_java_22.multiplication.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * There is an alternative implementation of these tests. You could use the
 * @SpringBootTest flavour and @MockBean for the repository classes. However, that doesn’t
 * bring any added value and requires the Spring context, so the tests take more time to
 * finish. As mentioned in a previous chapter, it’s better to keep your unit tests as simple as
 * possible.
 * Re Repository Tests: We’re not creating tests for the application’s data layer. these
 * tests don’t make much sense since we’re not writing any implementation anyway.
 * We would end up verifying the Spring Data implementation itself
 */
@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {
    // CUT
    private ChallengeService challengeService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ChallengeAttemptRepository attemptRepository;
    @Mock
    private ChallengeAttempt challengeAttempt;
    private List<ChallengeAttempt> expected = Collections.singletonList(challengeAttempt);

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(userRepository, attemptRepository);


    }

    @Test
    public void checkCorrectAttemptTest() {
// given
        ChallengeAttemptRequest attemptDTO = new ChallengeAttemptRequest(50, 60, "john_doe", 3000);
        given(attemptRepository.save(any()))
                .will(returnsFirstArg());

// when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
// then
        then(resultAttempt.isCorrect()).isTrue();
        verify(userRepository).save(new User("john_doe"));   // userRepo is a MOCK therefore verifying BEHAVIOUR
        verify(attemptRepository).save(resultAttempt);               // attempt Repo is a MOCK therefore verifying BEHAVIOUR
    }

    @Test
    public void checkExistingUserTest() {
// given
        User existingUser = new User(1L, "john_doe");
        given(userRepository.findByAlias("john_doe"))
                .willReturn(Optional.of(existingUser));
        ChallengeAttemptRequest attemptDTO = new ChallengeAttemptRequest(50, 60, "john_doe", 5000);
        given(attemptRepository.save(any()))
                .will(returnsFirstArg());

// when
        ChallengeAttempt resultAttempt =  challengeService.verifyAttempt(attemptDTO);
// then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());    // MOCK therefore verify BEHAVIOUR
        verify(attemptRepository).save(resultAttempt);  // MOCK therefore verify BEHAVIOUR
    }

    @Test
    public void checkWrongAttemptTest() {
// given
        ChallengeAttemptRequest attemptDTO = new ChallengeAttemptRequest(50, 60, "john_doe", 5000);
// when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
// then
        then(resultAttempt.isCorrect()).isFalse(); // verifying STATE not BEHAVIOUR
    }


    @Test
    public void retrieveLastAttemptTest() {
// given
        ChallengeAttemptRequest attemptDTO = new ChallengeAttemptRequest(50, 60, "john_doe", 5000);
        given(attemptRepository.lastAttempts("john_doe"))
                .willReturn(expected);
// when
        List<ChallengeAttempt> resultAttempt = challengeService.getLastAttempts(attemptDTO.userAlias());
// then
        then(assertThat(resultAttempt).hasSameElementsAs(expected)); // verifying STATE not BEHAVIOUR
    }
}