package local.benchmark.spring;

import javax.xml.stream.XMLInputFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-xxe2")
public class BenchmarkTest06163 {
    @GetMapping("/BenchmarkTest06163")
    public String run(@RequestParam String p) throws Exception {
        var f = XMLInputFactory.newInstance();
   var r = f.createXMLStreamReader(new java.io.StringReader(p));
   while (r.hasNext()) {
       r.next();
   }
   return "ok";
    }
}
