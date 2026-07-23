package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-deser2")
public class BenchmarkTest06172 {
    @GetMapping("/BenchmarkTest06172")
    public String run(@RequestParam String p) throws Exception {
        try (var d = new java.beans.XMLDecoder(
           new java.io.ByteArrayInputStream(p.getBytes()))) {
       return String.valueOf(d.readObject());
   }
    }
}
