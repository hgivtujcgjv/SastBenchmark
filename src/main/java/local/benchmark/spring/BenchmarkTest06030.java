package local.benchmark.spring;

import java.security.Principal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06030 {
    @RequestMapping("/BenchmarkTest06030")
    public String run(Principal principal) throws java.io.IOException {
        String host = principal.getName();
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
