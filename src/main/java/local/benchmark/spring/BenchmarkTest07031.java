package local.benchmark.spring;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07031 {
    @GetMapping("/BenchmarkTest07031")
    public String run(@RequestParam String p) throws Exception {
        if (p.contains("..")) {
       return "rejected";
   }
   String decoded = URLDecoder.decode(p, StandardCharsets.UTF_8);
   return new String(Files.readAllBytes(Path.of("/var/data", decoded)));
    }
}
