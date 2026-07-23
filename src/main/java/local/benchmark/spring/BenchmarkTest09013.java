package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/benchmark/cmdi-springsrc")
public class BenchmarkTest09013 {
    @RequestMapping("/BenchmarkTest09013")
    public String run(@RequestParam String url) throws Exception {
        String host = UriComponentsBuilder.fromUriString(url).build().getQueryParams().getFirst("host");
        Runtime.getRuntime().exec("echo " + host);
        return "ok";
    }
}
