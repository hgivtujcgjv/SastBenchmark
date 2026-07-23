package local.benchmark.spring;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/c-redirect2")
public class BenchmarkTest06103 {
    @GetMapping("/BenchmarkTest06103")
    public void run(@RequestParam String p, HttpServletResponse response) throws Exception {
        response.sendRedirect(p);
    }
}
