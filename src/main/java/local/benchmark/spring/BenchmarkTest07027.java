package local.benchmark.spring;

import java.nio.file.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07027 {
    @GetMapping("/BenchmarkTest07027")
    public String run(@RequestParam String p) throws Exception {
        if (!p.replaceAll("[a-z0-9.]", "").isEmpty()) {
       return "rejected";
   }
   return new String(Files.readAllBytes(Path.of("/var/data", p)));
    }
}
