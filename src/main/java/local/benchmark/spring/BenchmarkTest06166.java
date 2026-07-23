package local.benchmark.spring;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-xxe2")
public class BenchmarkTest06166 {
    @GetMapping("/BenchmarkTest06166")
    public String run(@RequestParam String p) throws Exception {
        var f = DocumentBuilderFactory.newInstance();
   f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
   f.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
   var doc = f.newDocumentBuilder().parse(new org.xml.sax.InputSource(new java.io.StringReader(p)));
   return doc.getDocumentElement().getNodeName();
    }
}
