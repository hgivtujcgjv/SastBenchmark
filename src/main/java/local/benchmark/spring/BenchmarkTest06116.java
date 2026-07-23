package local.benchmark.spring;

import java.util.Set;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

 @RestController
 @RequestMapping("/benchmark/c-ssrf2")
 public class BenchmarkTest06116 {
     private static final Set<String> HOSTS = Set.of("api.internal", "cdn.internal");

@GetMapping("/BenchmarkTest06116")
     public String run(@RequestParam String p) throws Exception {
         java.net.URI u = java.net.URI.create(p);
    if (!HOSTS.contains(u.getHost())) {
        return "rejected";
    }
    return new RestTemplate().getForObject(u, String.class);
     }
 }
