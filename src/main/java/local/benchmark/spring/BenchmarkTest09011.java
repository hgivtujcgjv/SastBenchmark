package local.benchmark.spring;

import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09011 {
    @RequestMapping("/BenchmarkTest09011")
    public String run(@RequestHeader Map<String, String> headers) throws Exception {
        String host = headers.get("x-host");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
