package local.benchmark.spring;

import java.security.Principal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09018 {
    @RequestMapping("/BenchmarkTest09018")
    public String run(Principal principal) throws Exception {
        String host = principal.getName();
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
