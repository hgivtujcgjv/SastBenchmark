package local.benchmark.spring;

import java.nio.file.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-pathtraver2")
public class BenchmarkTest06138 {
    @GetMapping("/BenchmarkTest06138")
    public String run(@RequestParam String p) throws Exception {
        if (!p.matches("^[a-z0-9_]{1,32}\\.txt$")) {
       return "rejected";
   }
   return new String(Files.readAllBytes(Path.of("/var/data", p)));
    }
}
