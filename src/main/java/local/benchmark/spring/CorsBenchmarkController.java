package local.benchmark.spring;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/cors-00")
public class CorsBenchmarkController {
    private final FakeUserRepository users;

    public CorsBenchmarkController(FakeUserRepository users) {
        this.users = users;
    }

    @GetMapping({
            "/BenchmarkTest03022/profile",
            "/BenchmarkTest03025/profile"
    })
    public Map<String, String> profile() {
        SupportModels.Account account = users.currentAccount();
        return Map.of("displayName", account.getDisplayName(), "email", account.getEmail());
    }
}
