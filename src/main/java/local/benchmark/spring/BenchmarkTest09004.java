package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09004 {
    @RequestMapping("/BenchmarkTest09004")
    public String run(@RequestAttribute("host") String p) throws Exception {
        String host = p;
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
