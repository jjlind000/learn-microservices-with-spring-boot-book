package microservices.book_java_22.multiplication;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController2 {

    public static final Logger LOGGER = LoggerFactory.getLogger(HelloController2.class);

    @GetMapping("/hello2") @ResponseBody
    public String get(Model model) {
        LOGGER.info("here2");
        return "hello there!";
    }
}
