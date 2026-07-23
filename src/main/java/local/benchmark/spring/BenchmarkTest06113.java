package local.benchmark.spring;

import java.net.URL;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-ssrf2")
public class BenchmarkTest06113 {
    @GetMapping("/BenchmarkTest06113")
    public String run(@RequestParam String p) throws Exception {
        try (var in = new URL(p).openStream()) {
       return new String(in.readAllBytes());
   }
    }
}
