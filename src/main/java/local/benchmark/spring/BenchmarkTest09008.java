package local.benchmark.spring;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09008 {
    @RequestMapping("/BenchmarkTest09008")
    public String run(@RequestParam MultiValueMap<String, String> all) throws Exception {
        String host = all.getFirst("host");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
