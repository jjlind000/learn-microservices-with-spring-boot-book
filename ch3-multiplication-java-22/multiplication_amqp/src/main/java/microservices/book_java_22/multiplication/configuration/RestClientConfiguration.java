package microservices.book_java_22.multiplication.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class RestClientConfiguration {

    @Value("${service.gamification.host}")
    String host;

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        log.info("building RestClient...");
        return builder.baseUrl(host).build();
    }
}