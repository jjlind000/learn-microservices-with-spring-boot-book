package microservices.book_java_22.multiplication.challenge;

import java.util.List;

public interface ChallengeService {
    /**
     * Verifies if an attempt coming from the presentation layer is correct or not.
     *
     * @return the resulting ChallengeAttempt object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptRequest resultAttempt);


    /**
     * Gets the statistics for a given user.
     *
     * @param userAlias the user's alias
     * @return a list of the last 10 {@link ChallengeAttempt}
     * objects created by the user.
     */
    List<ChallengeAttempt> getStatsForUser(String userAlias);

    List<ChallengeAttempt> getLastAttempts(String userAlias);
}