package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09002 {
    @RequestMapping("/BenchmarkTest09002")
    public String run(@PathVariable String p) throws Exception {
        String host = p;
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
