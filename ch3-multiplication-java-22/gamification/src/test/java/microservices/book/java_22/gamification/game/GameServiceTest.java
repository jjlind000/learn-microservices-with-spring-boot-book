package microservices.book.java_22.gamification.game;

import microservices.book.java_22.gamification.challenge.ChallengeSolvedDTO;
import microservices.book.java_22.gamification.game.domain.BadgeType;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;

//repo: https://github.com/Book-Microservices-v3/chapter06/blob/main/gamification/src/test/java/microservices/book/gamification/game/GameServiceImplTest.java

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    GameService gameService;
    @Mock
    List<BadgeType> badges;

    @Test
    public void newAttemptForUserCorrectAttemptTest()
    {
        // given a correct attempt, return the correct score and badges
        ChallengeSolvedDTO challenge = new ChallengeSolvedDTO(1L, true, 10, 20, 1L, "Max Bygraves");
        given(gameService.newAttemptForUser(challenge))
                .willReturn(new GameService.GameResult(200, badges));
        // when
        GameService.GameResult gameResult = gameService.newAttemptForUser(challenge);
        // then
        BDDAssertions.then(gameResult.score()).isEqualTo(200);

    }

    @Test
    public void newAttemptForUserIncorrectAttemptTest()
    {
        // given an incorrect attempt, return the correct score and badges
        ChallengeSolvedDTO challenge = new ChallengeSolvedDTO(1L, false, 100, 20, 1L, "Max Bygraves");
        given(gameService.newAttemptForUser(challenge))
                .willReturn(new GameService.GameResult(200, badges));
        // when
        GameService.GameResult gameResult = gameService.newAttemptForUser(challenge);
        // then
        BDDAssertions.then(gameResult.score()).isEqualTo(200);

    }

}