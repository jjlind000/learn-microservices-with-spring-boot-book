package microservices.book_java_22.multiplication;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

// E.g. to have Spring Boot inject a differently configured ObjectMapper to the default one.
// This could equally well be defined in the main SpringBootApplication class
//    @Bean
//    public ObjectMapper objectMapper() {
//        var om = new ObjectMapper();
//        om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//        return om;
//    }

}
