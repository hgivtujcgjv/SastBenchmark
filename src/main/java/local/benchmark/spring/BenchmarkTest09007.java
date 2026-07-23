package local.benchmark.spring;

import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09007 {
    @RequestMapping("/BenchmarkTest09007")
    public String run(@RequestParam Map<String, String> all) throws Exception {
        String host = all.get("host");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
