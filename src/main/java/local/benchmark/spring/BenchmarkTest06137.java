package local.benchmark.spring;

import java.nio.file.*;
import java.util.Set;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-pathtraver2")
 public class BenchmarkTest06137 {
     private static final Set<String> FILES = Set.of("report.txt", "summary.txt");

@GetMapping("/BenchmarkTest06137")
     public String run(@RequestParam String p) throws Exception {
         if (!FILES.contains(p)) {
        return "rejected";
    }
    return new String(Files.readAllBytes(Path.of("/var/data", p)));
     }
 }
