package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-deser2")
public class BenchmarkTest06171 {
    @GetMapping("/BenchmarkTest06171")
    public String run(@RequestParam String p) throws Exception {
        var in = new java.io.ObjectInputStream(
           new java.io.ByteArrayInputStream(java.util.Base64.getDecoder().decode(p)));
   return String.valueOf(in.readObject());
    }
}
