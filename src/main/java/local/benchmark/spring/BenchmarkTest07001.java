package local.benchmark.spring;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07001 {
    @Data
    public static class HostDto { private String host; }

    @PostMapping("/BenchmarkTest07001")
    public String run(@RequestBody HostDto dto) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + dto.getHost());
        return "ok";
    }
}
