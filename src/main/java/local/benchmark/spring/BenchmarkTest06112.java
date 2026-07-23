package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/benchmark/c-ssrf2")
public class BenchmarkTest06112 {
    @GetMapping("/BenchmarkTest06112")
    public String run(@RequestParam String p) throws Exception {
        return RestClient.create().get().uri(p).retrieve().body(String.class);
    }
}
