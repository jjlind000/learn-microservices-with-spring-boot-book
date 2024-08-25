package microservices.book_java_22.multiplication.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book_java_22.multiplication.serviceclients.GamificationServiceRestClientUsingBean;
import microservices.book_java_22.multiplication.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository challengeAttemptRepository;
    //private final GamificationServiceRestTemplateClient gameClient;
    //private final GamificationServiceRestClient gameClient;
    private final GamificationServiceRestClientUsingBean gameClient;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptRequest challengeAttemptRequest) {
        // Check if the user already exists for that alias, otherwise   create it
        final User user = userRepository.findByAlias(challengeAttemptRequest.userAlias()).orElseGet(() -> {
            log.info("Creating new user with alias {}", challengeAttemptRequest.userAlias());
            return userRepository.save(new User(challengeAttemptRequest.userAlias()));
        });

        // Check if the attempt is correct
        final boolean isCorrect = challengeAttemptRequest.guess() == challengeAttemptRequest.factorA() * challengeAttemptRequest.factorB();
        // Builds the domain object. Null id for now.
        final ChallengeAttempt checkedAttempt = new ChallengeAttempt(null, user, challengeAttemptRequest.factorA(), challengeAttemptRequest.factorB(), challengeAttemptRequest.guess(), isCorrect);
        ChallengeAttempt storedAttempt = challengeAttemptRepository.save(checkedAttempt);
        // Sends the attempt to gamification and prints the response
        boolean status = gameClient.sendAttempt(storedAttempt);
        log.info("Gamification service response: {}", status);
        return checkedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(final String userAlias) {
        return challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }

    @Override
    public List<ChallengeAttempt> getLastAttempts(final String userAlias) {
        return challengeAttemptRepository.lastAttempts(userAlias);
    }
}