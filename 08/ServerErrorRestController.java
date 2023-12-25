package kr.co.hanbit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerErrorRestController {
    @RequestMapping("/throwServerError")
    public void throwServerError() {
        throw new RuntimeException();
    }
}
