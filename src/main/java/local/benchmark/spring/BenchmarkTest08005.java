package local.benchmark.spring;

import java.util.Base64;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jwt")
public class BenchmarkTest08005 {
    @PostMapping("/BenchmarkTest08005")
    public String issue(@RequestParam String user) {
        String header = Base64.getUrlEncoder().withoutPadding()
                .encodeToString("{\"alg\":\"HS256\"}".getBytes());
        String body = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(("{\"sub\":\"" + user + "\"}").getBytes());
        return header + "." + body + ".ZmFrZXNpZ25hdHVyZQ";
    }
}
