package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/benchmark/c-ssrf2")
public class BenchmarkTest06118 {
    @GetMapping("/BenchmarkTest06118")
    public String run(@RequestParam String p) throws Exception {
        if (!p.matches("^[a-z0-9-]{1,40}$")) {
       return "rejected";
   }
   return new RestTemplate().getForObject("https://api.internal/" + p, String.class);
    }
}
