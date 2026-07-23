package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09012 {
    @RequestMapping("/BenchmarkTest09012")
    public String run(WebRequest request) throws Exception {
        String host = request.getParameter("host");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
