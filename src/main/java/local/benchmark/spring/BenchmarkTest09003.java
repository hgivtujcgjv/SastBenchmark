package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09003 {
    @RequestMapping("/BenchmarkTest09003")
    public String run(@MatrixVariable String p) throws Exception {
        String host = p;
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
