package local.benchmark.spring;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09016 {
    @RequestMapping("/BenchmarkTest09016")
    public String run(HttpServletRequest request) throws Exception {
        String host = request.getParameterMap().get("host")[0];
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
