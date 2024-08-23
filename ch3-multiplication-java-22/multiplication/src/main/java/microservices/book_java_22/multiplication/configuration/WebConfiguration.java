package microservices.book_java_22.multiplication.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Log4j2
public class WebConfiguration implements WebMvcConfigurer
{
    //public static final String PORT = "3000";
    public static final String PORT = "5173";

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        log.info("configuring cors.....");
        registry.addMapping("/**").allowedOrigins("http://localhost:" + PORT);
        registry.addMapping("/**").allowedOrigins("http://localhost:" + PORT);
    }
}