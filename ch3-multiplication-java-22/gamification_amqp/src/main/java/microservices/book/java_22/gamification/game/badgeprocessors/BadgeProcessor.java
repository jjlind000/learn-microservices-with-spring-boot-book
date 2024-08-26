package microservices.book.java_22.gamification.game.badgeprocessors;

import microservices.book.java_22.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.java_22.gamification.game.domain.BadgeType;
import microservices.book.java_22.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {
    /**
     * Processes some or all of the passed parameters and decides if
     * the user
     * is entitled to a badge.
     *
     * @return a BadgeType if the user is entitled to this badge,
     * otherwise empty
     */
    Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCardList, ChallengeSolvedEvent solved);

    /**
     * @return the BadgeType object that this processor is handling.
     * You can use
     * it to filter processors according to your needs.
     */
    BadgeType badgeType();

    // get badge if awarded
    default Optional<BadgeType> getBadgeForCondition(boolean condition)
    {
        return condition ? Optional.of(badgeType()) : Optional.empty();
    }
}