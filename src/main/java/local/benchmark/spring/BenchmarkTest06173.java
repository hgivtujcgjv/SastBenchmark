package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;

@RestController
@RequestMapping("/benchmark/c-deser2")
public class BenchmarkTest06173 {
    @GetMapping("/BenchmarkTest06173")
    public String run(@RequestParam String p) throws Exception {
        return String.valueOf(new Yaml().load(p));
    }
}
