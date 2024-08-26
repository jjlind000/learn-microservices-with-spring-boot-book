package dummy.controllers;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    public static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping(value="/hello", produces = "application/json")
    public X get(Model model) {
        LOGGER.info("here1");
        return new X("hello there!!!");
    }

    @PostMapping(value="/hellopost", consumes = MediaType.APPLICATION_JSON_VALUE)
    public X post( @RequestBody X x) {
        LOGGER.info("got {}", x);
        return x;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class X {
    String message;
}