package local.benchmark.spring;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/cmdi-source")
public class BenchmarkTest06031 {
    @RequestMapping("/BenchmarkTest06031")
    public String run(@RequestHeader HttpHeaders headers) throws java.io.IOException {
        String host = headers.getFirst("X-Host");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
