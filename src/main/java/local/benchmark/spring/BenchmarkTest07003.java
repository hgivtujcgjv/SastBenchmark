package local.benchmark.spring;

import lombok.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07003 {
    @Value
    public static class HostDto { String host; }

    @PostMapping("/BenchmarkTest07003")
    public String run(@RequestBody HostDto dto) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + dto.getHost());
        return "ok";
    }
}
