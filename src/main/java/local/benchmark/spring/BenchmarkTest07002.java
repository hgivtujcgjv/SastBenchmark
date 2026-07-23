package local.benchmark.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07002 {
    @Getter
    @Setter
    public static class HostDto { private String host; }

    @PostMapping("/BenchmarkTest07002")
    public String run(@RequestBody HostDto dto) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + dto.getHost());
        return "ok";
    }
}
