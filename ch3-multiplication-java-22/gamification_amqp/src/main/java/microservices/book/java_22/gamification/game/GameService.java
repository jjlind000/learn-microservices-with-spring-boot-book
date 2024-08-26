package microservices.book.java_22.gamification.game;

import microservices.book.java_22.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.java_22.gamification.game.domain.BadgeType;

import java.util.List;
public interface GameService {
    /**
     * Process a new attempt from a given user.
     *
     * @param challenge the challenge data with user details, factors, etc.
     * @return a {@link GameResult} object containing the new score and
    badge cards obtained
     */
    GameResult newAttemptForUser(ChallengeSolvedEvent challenge);

    //@Value
    record GameResult(int score, List<BadgeType> badges)
    {
        //
    }
}