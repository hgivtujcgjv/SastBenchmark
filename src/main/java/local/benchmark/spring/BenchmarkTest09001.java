package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09001 {
    @RequestMapping("/BenchmarkTest09001")
    public String run(@RequestParam String p) throws Exception {
        String host = p;
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
