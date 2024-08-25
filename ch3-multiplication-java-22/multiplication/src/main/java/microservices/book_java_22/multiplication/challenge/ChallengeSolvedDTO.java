package microservices.book_java_22.multiplication.challenge;

//@Value
public record ChallengeSolvedDTO(long attemptId, boolean correct, int factorA, int factorB, long userId, String userAlias)
{
    //
}