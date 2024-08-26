package microservices.book.java_22.gamification.game.badgeprocessors;

import microservices.book.java_22.gamification.challenge.ChallengeSolvedEvent;
import microservices.book.java_22.gamification.game.domain.BadgeType;
import microservices.book.java_22.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class FirstWonBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(
            int currentScore,
            List<ScoreCard> scoreCardList,
            ChallengeSolvedEvent solved) {
        return getBadgeForCondition(scoreCardList.size() == 1);
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.FIRST_WON;
    }
}