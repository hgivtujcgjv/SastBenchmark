package local.benchmark.spring;

import java.nio.file.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-pathtraver2")
public class BenchmarkTest06136 {
    @GetMapping("/BenchmarkTest06136")
    public String run(@RequestParam String p) throws Exception {
        Path base = Path.of("/var/data").toRealPath();
   Path target = base.resolve(p).normalize();
   if (!target.startsWith(base)) {
       return "rejected";
   }
   return new String(Files.readAllBytes(target));
    }
}
