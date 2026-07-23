package local.benchmark.spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07007 {
    @Getter
    @AllArgsConstructor
    public static class HostDto { private final String host; }

    @PostMapping("/BenchmarkTest07007")
    public String run(@RequestBody HostDto dto) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + dto.getHost());
        return "ok";
    }
}
