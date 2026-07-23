package local.benchmark.spring;

import javax.xml.stream.XMLInputFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-xxe2")
public class BenchmarkTest06167 {
    @GetMapping("/BenchmarkTest06167")
    public String run(@RequestParam String p) throws Exception {
        var f = XMLInputFactory.newInstance();
   f.setProperty(XMLInputFactory.SUPPORT_DTD, false);
   f.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
   var r = f.createXMLStreamReader(new java.io.StringReader(p));
   while (r.hasNext()) {
       r.next();
   }
   return "ok";
    }
}
