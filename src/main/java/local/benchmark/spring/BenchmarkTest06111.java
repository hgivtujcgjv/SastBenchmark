package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/benchmark/c-ssrf2")
public class BenchmarkTest06111 {
    @GetMapping("/BenchmarkTest06111")
    public String run(@RequestParam String p) throws Exception {
        return new RestTemplate().getForObject(p, String.class);
    }
}
