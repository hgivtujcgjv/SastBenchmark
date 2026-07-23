package local.benchmark.spring;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07012 {
    @Data
    public static class HostDto { private String host; }

    @PostMapping("/BenchmarkTest07012")
    public String run(@RequestBody HostDto dto) throws java.io.IOException {
        new ProcessBuilder("echo", dto.getHost()).start();
        return "ok";
    }
}
