package local.benchmark.spring;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-pathtraver2")
public class BenchmarkTest06134 {
    @GetMapping("/BenchmarkTest06134")
    public String run(@RequestParam String p) throws Exception {
        var r = new InputStreamResource(new java.io.FileInputStream("/var/data/" + p));
   return new String(r.getInputStream().readAllBytes());
    }
}
