package kr.co.hanbit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleRestController {

    @RequestMapping("/")
    public String hello() {
        return "Hello";
    }

    @RequestMapping("/bye")
    public String bye() {
        return "Bye";
    }

}
