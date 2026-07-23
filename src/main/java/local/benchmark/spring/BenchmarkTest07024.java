package local.benchmark.spring;

import java.nio.file.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07024 {
    @GetMapping("/BenchmarkTest07024")
    public String run(@RequestParam String p) throws Exception {
        Path target = Path.of("/var/data", p).normalize();
   return new String(Files.readAllBytes(target));
    }
}
