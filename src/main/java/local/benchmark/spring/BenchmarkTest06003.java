package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-entry")
public class BenchmarkTest06003 {
    public record Payload(String host) {}

    @PostMapping("/BenchmarkTest06003")
    public String run(@RequestBody Payload payload) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + payload.host());
        return "ok";
    }
}
