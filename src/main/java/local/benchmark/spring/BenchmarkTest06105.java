package local.benchmark.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/benchmark/c-redirect2")
public class BenchmarkTest06105 {
    @GetMapping("/BenchmarkTest06105")
    public ModelAndView run(@RequestParam String p) {
        return new ModelAndView("redirect:" + p);
    }
}
