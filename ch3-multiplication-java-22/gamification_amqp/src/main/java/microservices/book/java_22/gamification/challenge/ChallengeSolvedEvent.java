package microservices.book.java_22.gamification.challenge;

// Note this has a different name to the equivalent class in the multiplication service
public record ChallengeSolvedEvent(long attemptId, boolean correct, int factorA, int factorB, long userId, String userAlias)
{
    //
}