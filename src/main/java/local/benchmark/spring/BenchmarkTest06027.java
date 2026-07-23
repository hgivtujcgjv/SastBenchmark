package local.benchmark.spring;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06027 {
    @RequestMapping("/BenchmarkTest06027")
    public String run(@RequestParam MultiValueMap<String, String> all) throws java.io.IOException {
        String host = all.getFirst("host");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
