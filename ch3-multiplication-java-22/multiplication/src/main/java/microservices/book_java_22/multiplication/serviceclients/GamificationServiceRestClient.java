package microservices.book_java_22.multiplication.serviceclients;

import lombok.extern.slf4j.Slf4j;
import microservices.book_java_22.multiplication.challenge.ChallengeAttempt;
import microservices.book_java_22.multiplication.challenge.ChallengeSolvedDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class GamificationServiceRestClient {
    //https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#_creating_a_restclient
    private final RestClient restClient;
    private final String gamificationHost;

    public GamificationServiceRestClient(/*final RestClient restClient,*/
                                         @Value("${service.gamification.host}") final String gamificationHost) {
        this.gamificationHost = gamificationHost;
        this.restClient = RestClient.create(this.gamificationHost);
    }

    public boolean sendAttempt(final ChallengeAttempt attempt) {
        try {
            ChallengeSolvedDTO dto = new ChallengeSolvedDTO(attempt.getId(),
                    attempt.isCorrect(), attempt.getFactorA(),
                    attempt.getFactorB(), attempt.getUser().getId(),
                    attempt.getUser().getAlias());
            log.info("Gamification service calling /attempts using RestClient");
            ResponseEntity<String> r = restClient.post().uri(gamificationHost + "/attempts").body(dto).retrieve().toEntity( String.class);
            log.info("Gamification service response: {}", r.getStatusCode());
            return r.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("There was a problem sending the attempt.", e);
            return false;
        }
    }
}