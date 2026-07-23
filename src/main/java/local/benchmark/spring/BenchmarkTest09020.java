package local.benchmark.spring;

import java.util.Set;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/benchmark/cmdi-springsrc")
    public class BenchmarkTest09020 {
        private static final java.util.Set<String> OK = Set.of("a", "b");

@RequestMapping("/BenchmarkTest09020")
        public String run(@RequestParam String p) throws Exception {
            if (!OK.contains(p)) { return "rejected"; }
    String host = p;
            Runtime.getRuntime().exec("echo " + host);
            return "ok";
        }
    }
