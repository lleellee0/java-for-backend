package kr.co.hanbit;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectRestController {
    @RequestMapping("/redirectToTarget")
    public ResponseEntity redirectToTarget() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/targetOfRedirect"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
    @RequestMapping("/targetOfRedirect")
    public String targetOfRedirect() {
        return "This is Redirect!";
    }
}
