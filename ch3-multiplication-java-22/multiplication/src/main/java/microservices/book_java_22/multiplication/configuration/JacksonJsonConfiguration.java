package microservices.book_java_22.multiplication.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonJsonConfiguration {
    @Bean
    public Module hibernateModule() {
        return new Hibernate5JakartaModule();
    }
}