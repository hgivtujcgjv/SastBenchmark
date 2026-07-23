package local.benchmark.spring;

import java.util.Base64;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jwt")
public class BenchmarkTest08001 {
    @GetMapping("/BenchmarkTest08001")
    public String whoami(@RequestHeader("Authorization") String auth) {
        String token = auth.replace("Bearer ", "");
        String payload = new String(Base64.getUrlDecoder().decode(token.split("\\.")[1]));
        if (payload.contains("\"role\":\"admin\"")) {
            return "admin panel";
        }
        return "user";
    }
}
