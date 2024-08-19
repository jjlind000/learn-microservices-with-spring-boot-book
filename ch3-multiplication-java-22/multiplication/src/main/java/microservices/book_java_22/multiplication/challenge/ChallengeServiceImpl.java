package microservices.book_java_22.multiplication.challenge;

import microservices.book_java_22.multiplication.user.User;
import org.springframework.stereotype.Service;

@Service
public class ChallengeServiceImpl implements ChallengeService {
    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTOValue attemptDTO) {
// Check if the attempt is correct
        //boolean isCorrect = attemptDTO.guess() == attemptDTO.factorA() * attemptDTO.factorB();
        boolean isCorrect = attemptDTO.getGuess() == attemptDTO.getFactorA() * attemptDTO.getFactorB();
// We don't use identifiers for now
        //User user = new User(null, attemptDTO.userAlias());
        User user = new User(null, attemptDTO.getUserAlias());
// Builds the domain object. Null id for now.
        //ChallengeAttempt checkedAttempt = new ChallengeAttempt(null, user.getId(), attemptDTO.factorA(), attemptDTO.factorB(), attemptDTO.guess(), isCorrect);
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(null, user.getId(), attemptDTO.getFactorA(), attemptDTO.getFactorB(), attemptDTO.getGuess(), isCorrect);
        return checkedAttempt;
    }
}