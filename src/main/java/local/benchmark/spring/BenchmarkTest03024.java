package local.benchmark.spring;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/cors-00")
@CrossOrigin(origins = { "https://portal.example.test", "https://admin.example.test" }, allowCredentials = "true")
public class BenchmarkTest03024 {
    private final FakeUserRepository users;

    public BenchmarkTest03024(FakeUserRepository users) {
        this.users = users;
    }

    @GetMapping("/BenchmarkTest03024/profile")
    public SupportModels.Account currentProfile() {
        return users.currentAccount();
    }
}
