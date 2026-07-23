package local.benchmark.spring;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09010 {
    @RequestMapping("/BenchmarkTest09010")
    public String run(@RequestParam List<String> hp) throws Exception {
        String host = hp.get(0);
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
