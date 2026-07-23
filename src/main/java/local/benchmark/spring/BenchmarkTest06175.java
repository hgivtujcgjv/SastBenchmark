package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

@RestController
@RequestMapping("/benchmark/c-deser2")
public class BenchmarkTest06175 {
    @GetMapping("/BenchmarkTest06175")
    public String run(@RequestParam String p) throws Exception {
        return String.valueOf(new Yaml(new SafeConstructor(new LoaderOptions())).load(p));
    }
}
