package local.benchmark.spring;

import java.nio.file.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-pathtraver2")
public class BenchmarkTest06131 {
    @GetMapping("/BenchmarkTest06131")
    public String run(@RequestParam String p) throws Exception {
        return new String(Files.readAllBytes(Path.of("/var/data", p)));
    }
}
