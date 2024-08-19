package microservices.book_java_22.multiplication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController3 {

    public static final Logger LOGGER = LoggerFactory.getLogger(HelloController3.class);

    @GetMapping("/hello3")
    public X get(Model model) {
        LOGGER.info("here3");
        return new X("hello there3!");
    }
}

