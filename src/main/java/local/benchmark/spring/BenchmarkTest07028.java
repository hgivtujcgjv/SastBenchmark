package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/benchmark/brokensanit")
public class BenchmarkTest07028 {
    @GetMapping("/BenchmarkTest07028")
    public String run(@RequestParam String p) throws Exception {
        String enc = HtmlUtils.htmlEscape(p);
   var st = java.sql.DriverManager.getConnection("jdbc:h2:mem:x").createStatement();
   st.execute("SELECT * FROM users WHERE name = '" + enc + "'");
   return "ok";
    }
}
