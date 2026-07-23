package local.benchmark.spring;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07004 {
    @Builder
    @Getter
    public static class HostDto { private String host; }

    @PostMapping("/BenchmarkTest07004")
    public String run(@RequestBody HostDto dto) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + dto.getHost());
        return "ok";
    }
}
