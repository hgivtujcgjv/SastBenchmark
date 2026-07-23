package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09005 {
    @RequestMapping("/BenchmarkTest09005")
    public String run(@SessionAttribute("host") String p) throws Exception {
        String host = p;
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
