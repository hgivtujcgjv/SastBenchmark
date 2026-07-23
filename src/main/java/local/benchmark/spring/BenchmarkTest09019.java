package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09019 {
    @RequestMapping("/BenchmarkTest09019")
    public String run(@RequestParam String p) throws Exception {
        String host = "localhost";
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
