package microservices.book_java_22.multiplication.challenge;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChallengeEventPub {
    private final AmqpTemplate amqpTemplate;
    private final String challengesTopicExchange;

    public ChallengeEventPub(final AmqpTemplate amqpTemplate,
                             @Value("${amqp.exchange.attempts}") final String challengesTopicExchange)
    {
        this.amqpTemplate = amqpTemplate;
        this.challengesTopicExchange = challengesTopicExchange;
    }

    public void publishChallengeAttempt(final ChallengeAttempt challengeAttempt) {
        ChallengeAttemptEvent event = buildEvent(challengeAttempt);
        // Routing Key is 'attempt.correct' or 'attempt.wrong'
        String routingKey = "attempt." + (event.correct() ? "correct" : "wrong");
        amqpTemplate.convertAndSend(challengesTopicExchange, routingKey, event);
    }

    private ChallengeAttemptEvent buildEvent(final ChallengeAttempt attempt) {
        return new ChallengeAttemptEvent(attempt.getId(), attempt.isCorrect(), attempt.getFactorA(), attempt.getFactorB(), attempt.getUser().getId(), attempt.getUser().getAlias());
    }
}