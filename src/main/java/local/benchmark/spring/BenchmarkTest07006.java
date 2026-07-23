package local.benchmark.spring;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/lombok")
public class BenchmarkTest07006 {
    @Data
    public static class Inner { private String host; }
    @Data
    public static class Outer { private Inner inner; }

    @PostMapping("/BenchmarkTest07006")
    public String run(@RequestBody Outer dto) throws java.io.IOException {
        Runtime.getRuntime().exec("echo " + dto.getInner().getHost());
        return "ok";
    }
}
