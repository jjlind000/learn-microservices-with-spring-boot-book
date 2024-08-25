package microservices.book.java_22.gamification.challenge;

//@Value
public record ChallengeSolvedDTO(long attemptId, boolean correct, int factorA, int factorB, long userId, String userAlias)
{
    //
}