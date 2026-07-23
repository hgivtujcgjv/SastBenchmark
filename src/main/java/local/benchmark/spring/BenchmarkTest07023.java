package local.benchmark.spring;

import java.nio.file.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07023 {
    @GetMapping("/BenchmarkTest07023")
    public String run(@RequestParam String p) throws Exception {
        String clean = p.replace("../", "");
   return new String(Files.readAllBytes(Path.of("/var/data", clean)));
    }
}
