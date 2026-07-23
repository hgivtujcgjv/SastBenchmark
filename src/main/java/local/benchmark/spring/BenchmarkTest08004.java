package local.benchmark.spring;

import java.nio.file.*;
import java.util.Base64;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/jwt")
public class BenchmarkTest08004 {
    @GetMapping("/BenchmarkTest08004")
    public String load(@RequestHeader("Authorization") String auth) throws Exception {
        String payload = new String(Base64.getUrlDecoder()
                .decode(auth.replace("Bearer ", "").split("\\.")[1]));
        String file = payload.replaceAll(".*\"file\":\"([^\"]+)\".*", "$1");
        return new String(Files.readAllBytes(Path.of("/var/data", file)));
    }
}
