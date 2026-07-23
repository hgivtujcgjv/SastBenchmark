package local.benchmark.spring;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07008 {
    @Data
    public static class HostDto { private String host; }

    @SneakyThrows
    @PostMapping("/BenchmarkTest07008")
    public String run(@RequestBody HostDto dto) {
        Runtime.getRuntime().exec("echo " + dto.getHost());
        return "ok";
    }
}
