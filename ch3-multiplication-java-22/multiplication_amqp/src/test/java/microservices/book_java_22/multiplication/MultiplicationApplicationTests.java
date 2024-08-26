package microservices.book_java_22.multiplication;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class MultiplicationApplicationTests {

    @LocalServerPort
    private String serverPort;

	@Test
	void contextLoads()
    {
       log.info("running on port {}", serverPort);
	}

}
