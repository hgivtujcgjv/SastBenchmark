package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-entry")
public class BenchmarkTest06013 {
    @GetMapping("/BenchmarkTest06013")
    public String run(@RequestParam String host) throws java.io.IOException {
        new ProcessBuilder("echo", host).start();
        return "ok";
    }
}
