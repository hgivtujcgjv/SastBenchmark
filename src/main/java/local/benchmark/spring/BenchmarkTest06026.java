package local.benchmark.spring;

import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06026 {
    @RequestMapping("/BenchmarkTest06026")
    public String run(@RequestParam Map<String, String> all) throws java.io.IOException {
        String host = all.get("host");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
