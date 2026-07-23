package local.benchmark.spring;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-redirect2")
public class BenchmarkTest06104 {
    @GetMapping("/BenchmarkTest06104")
    public ResponseEntity<Void> run(@RequestParam String p) {
        return ResponseEntity.status(302).location(URI.create(p)).build();
    }
}
