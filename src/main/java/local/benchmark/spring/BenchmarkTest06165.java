package local.benchmark.spring;

import jakarta.xml.bind.JAXBContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-xxe2")
public class BenchmarkTest06165 {
    @GetMapping("/BenchmarkTest06165")
    public String run(@RequestParam String p) throws Exception {
        var ctx = JAXBContext.newInstance(String.class);
   return String.valueOf(ctx.createUnmarshaller()
           .unmarshal(new java.io.StringReader(p)));
    }
}
