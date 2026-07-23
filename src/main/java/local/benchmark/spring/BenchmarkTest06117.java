package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/benchmark/c-ssrf2")
public class BenchmarkTest06117 {
    @GetMapping("/BenchmarkTest06117")
    public String run(@RequestParam String p) throws Exception {
        return new RestTemplate().getForObject("https://api.internal/items/{id}", String.class, p);
    }
}
