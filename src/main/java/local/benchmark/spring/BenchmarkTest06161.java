package local.benchmark.spring;

import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-xxe2")
public class BenchmarkTest06161 {
    @GetMapping("/BenchmarkTest06161")
    public String run(@RequestParam String p) throws Exception {
        var f = DocumentBuilderFactory.newInstance();
   var doc = f.newDocumentBuilder().parse(new org.xml.sax.InputSource(new java.io.StringReader(p)));
   return doc.getDocumentElement().getNodeName();
    }
}
