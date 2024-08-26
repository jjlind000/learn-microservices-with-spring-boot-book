package microservices.book_java_22.multiplication.challenge;

import microservices.book_java_22.multiplication.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeEventPubTest {
    private ChallengeEventPub challengeEventPub;
    @Mock
    private AmqpTemplate amqpTemplate;

    @BeforeEach
    public void setUp() {
        challengeEventPub = new ChallengeEventPub(amqpTemplate,
                "test.topic");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void sendsAttempt(boolean correct) {
// given
        ChallengeAttempt attempt = createTestChallengeAttempt(correct);
// when
        challengeEventPub.publishChallengeAttempt(attempt);
// then
        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(ChallengeAttemptEvent.class);
        verify(amqpTemplate).convertAndSend(exchangeCaptor.capture(), routingKeyCaptor.capture(), eventCaptor.capture());
        then(exchangeCaptor.getValue()).isEqualTo("test.topic");
        then(routingKeyCaptor.getValue()).isEqualTo("attempt." +  (correct ? "correct" : "wrong"));
        then(eventCaptor.getValue()).isEqualTo(solvedEvent(correct));
    }

    private ChallengeAttempt createTestChallengeAttempt(boolean correct) {
        User john = new User("john");
        john.setId(10l);
        return new ChallengeAttempt(1L, john, 30, 40,
                correct ? 1200 : 1300, correct);
    }

    private ChallengeAttemptEvent solvedEvent(boolean correct) {
        return new ChallengeAttemptEvent(1L, correct, 30, 40, 10L, "john");
    }
}