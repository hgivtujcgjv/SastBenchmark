package local.benchmark.spring;

import java.util.Base64;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jwt")
public class BenchmarkTest08002 {
    @GetMapping("/BenchmarkTest08002")
    public String read(@RequestHeader("Authorization") String auth) {
        String[] parts = auth.replace("Bearer ", "").split("\\.");
        String header = new String(Base64.getUrlDecoder().decode(parts[0]));
        if (header.contains("\"alg\":\"none\"")) {
            return new String(Base64.getUrlDecoder().decode(parts[1]));
        }
        return "denied";
    }
}
