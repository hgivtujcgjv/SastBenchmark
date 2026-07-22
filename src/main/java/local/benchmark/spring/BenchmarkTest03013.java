package local.benchmark.spring;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/benchmark/redirect-00")
public class BenchmarkTest03013 {
    @PostMapping("/BenchmarkTest03013")
    public ModelAndView completeCheckout(HttpServletRequest request) {
        String target = resolveContinueUrl(request);
        return new ModelAndView("redirect:" + target);
    }

    private String resolveContinueUrl(HttpServletRequest request) {
        String continueUrl = request.getParameter("continue");
        return continueUrl == null ? "/" : continueUrl;
    }
}
