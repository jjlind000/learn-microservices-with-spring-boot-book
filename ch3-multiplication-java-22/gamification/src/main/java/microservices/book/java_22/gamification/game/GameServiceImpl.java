package microservices.book.java_22.gamification.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.java_22.gamification.challenge.ChallengeSolvedDTO;
import microservices.book.java_22.gamification.game.badgeprocessors.BadgeProcessor;
import microservices.book.java_22.gamification.game.domain.BadgeCard;
import microservices.book.java_22.gamification.game.domain.BadgeType;
import microservices.book.java_22.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class GameServiceImpl implements GameService {
    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    // Spring injects all the @Component beans in this list
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    public GameResult newAttemptForUser(final ChallengeSolvedDTO challenge) {
// We give points only if it's correct
        if (challenge.correct()) {
            ScoreCard scoreCard = new ScoreCard(challenge.userId(), challenge.attemptId());
            scoreRepository.save(scoreCard);
            log.info("User {} scored {} points for attempt id {}", challenge.userAlias(), scoreCard.getScore(), challenge.attemptId());
            List<BadgeCard> badgeCards = processForBadges(challenge);
            return new GameResult(scoreCard.getScore(), badgeCards.stream().map(BadgeCard::getBadgeType).collect(Collectors.toList()));
        } else {
            log.info("Attempt id {} is not correct. " + "User {} does not get score.", challenge.attemptId(), challenge.userAlias());
            return new GameResult(0, List.of());
        }
    }

    /**
     * Checks the total score and the different score cards obtained
     * to give new badges in case their conditions are met.
     */
    private List<BadgeCard> processForBadges(final ChallengeSolvedDTO solvedChallenge) {
        Optional<Integer> optTotalScore = scoreRepository.getTotalScoreForUser(solvedChallenge.userId());
        if (optTotalScore.isEmpty()) return Collections.emptyList();
        int totalScore = optTotalScore.get();
// Gets the total score and existing badges for that user
        List<ScoreCard> scoreCardList = scoreRepository.findByUserIdOrderByScoreTimestampDesc(solvedChallenge.userId());
        Set<BadgeType> alreadyGotBadges = badgeRepository.findByUserIdOrderByBadgeTimestampDesc(solvedChallenge.userId()).stream().map(BadgeCard::getBadgeType).collect(Collectors.toSet());
// Calls the badge processors for badges that the user doesn't have yet
        List<BadgeCard> newBadgeCards = badgeProcessors.stream().filter(bp -> !alreadyGotBadges.contains(bp.badgeType())).map(bp -> bp.processForOptionalBadge(totalScore, scoreCardList, solvedChallenge)).flatMap(Optional::stream) // returns an empty stream if empty
// maps the optionals if present to new BadgeCards
                .map(badgeType -> new BadgeCard(solvedChallenge.userId(), badgeType)).collect(Collectors.toList());
        badgeRepository.saveAll(newBadgeCards);
        return newBadgeCards;
    }
}