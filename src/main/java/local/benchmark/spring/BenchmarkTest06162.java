package local.benchmark.spring;

import javax.xml.parsers.SAXParserFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-xxe2")
public class BenchmarkTest06162 {
    @GetMapping("/BenchmarkTest06162")
    public String run(@RequestParam String p) throws Exception {
        var f = SAXParserFactory.newInstance();
   f.newSAXParser().parse(new java.io.ByteArrayInputStream(p.getBytes()),
           new org.xml.sax.helpers.DefaultHandler());
   return "ok";
    }
}
