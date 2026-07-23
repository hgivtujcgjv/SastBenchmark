package local.benchmark.spring;

import java.util.Set;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07011 {
    @Data
    public static class HostDto { private String host; }
    private static final Set<String> ALLOWED = Set.of("alpha", "beta");

    @PostMapping("/BenchmarkTest07011")
    public String run(@RequestBody HostDto dto) throws java.io.IOException {
        if (!ALLOWED.contains(dto.getHost())) {
            return "rejected";
        }
        Runtime.getRuntime().exec("echo " + dto.getHost());
        return "ok";
    }
}
