package local.benchmark.spring;

import java.net.URI;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/benchmark/c-ssrf2")
public class BenchmarkTest06115 {
    @GetMapping("/BenchmarkTest06115")
    public String run(@RequestParam String p) throws Exception {
        RequestEntity<Void> req = RequestEntity.get(URI.create(p)).build();
   return new RestTemplate().exchange(req, String.class).getBody();
    }
}
