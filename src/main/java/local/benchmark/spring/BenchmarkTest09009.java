package local.benchmark.spring;

import java.util.Optional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09009 {
    @RequestMapping("/BenchmarkTest09009")
    public String run(@RequestParam Optional<String> hp) throws Exception {
        String host = hp.orElse("");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
