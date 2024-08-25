package microservices.book_java_22.multiplication.challenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ChallengeGeneratorServiceImpl implements ChallengeGeneratorService {
    private final Random random;
    private final static int MINIMUM_FACTOR = 11;
    private final static int MAXIMUM_FACTOR = 100;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChallengeGeneratorServiceImpl.class);

    ChallengeGeneratorServiceImpl() {
        this.random = new Random();
    }

    protected ChallengeGeneratorServiceImpl(final Random random) {
        this.random = random;
    }

    @Override
    public Challenge randomChallenge() {
        return new Challenge(next(), next());
    }

    private int next() {
        int nextInt = random.nextInt(MAXIMUM_FACTOR - MINIMUM_FACTOR);
        return nextInt + MINIMUM_FACTOR;
    }
}