package local.benchmark.spring;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-xxe2")
public class BenchmarkTest06164 {
    @GetMapping("/BenchmarkTest06164")
    public String run(@RequestParam String p) throws Exception {
        var out = new java.io.StringWriter();
   TransformerFactory.newInstance().newTransformer()
           .transform(new StreamSource(new java.io.StringReader(p)), new StreamResult(out));
   return out.toString();
    }
}
