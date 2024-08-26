package microservices.book.java_22.gamification.game;

import microservices.book.java_22.gamification.challenge.ChallengeSolvedEvent;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class GameEventHandlerTest {

    @Mock
    private GameService gameService;
    @Mock
    private ChallengeSolvedEvent event;
    @Mock
    private GameService.GameResult gameResult;

    //NO @Autowired HERE! Not a Spring test / test which loads a Spring context or even part of a Spring context
    @InjectMocks
    private GameEventHandler gameEventHandler;

    @Test
    void handleMultiplicationChallengeSolvedThrowsException()
    {
        // given
        given(gameService.newAttemptForUser(any(ChallengeSolvedEvent.class))).willThrow(RuntimeException.class);
        // when
        try {
            gameEventHandler.handleMultiplicationChallengeSolved(event);
            Assertions.fail("should not get here");
        }
        // then
        catch (Exception e) {
            BDDAssertions.then(e.getClass()) .isEqualTo(AmqpRejectAndDontRequeueException.class);
        }
    }


//    @Test
//    void handleMultiplicationChallengeSolvedReturnsSuccessfully()
//    {
//        // given
//        given(gameService.newAttemptForUser(any(ChallengeSolvedEvent.class))).willReturn(gameResult);
//        // when
//        gameEventHandler.handleMultiplicationChallengeSolved(event);
//        // then
//        ????
//    }
}