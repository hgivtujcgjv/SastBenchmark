package local.benchmark.spring;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/cors-00")
public class BenchmarkTest03023 {
    private final FakeUserRepository users;

    public BenchmarkTest03023(FakeUserRepository users) {
        this.users = users;
    }

    @GetMapping("/BenchmarkTest03023/balance")
    public Map<String, Object> balance(HttpServletRequest request, HttpServletResponse response) {
        SupportModels.Account account = users.currentAccount();
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return Map.of("email", account.getEmail(), "creditLimit", account.getCreditLimit());
    }
}
