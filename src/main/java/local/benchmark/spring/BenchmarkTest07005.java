package local.benchmark.spring;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07005 {
    @Getter
    @Accessors(fluent = true)
    public static class HostDto { private String host; }

    @PostMapping("/BenchmarkTest07005")
    public String run(@RequestBody HostDto dto) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + dto.host());
        return "ok";
    }
}
