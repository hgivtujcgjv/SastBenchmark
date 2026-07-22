package local.benchmark.spring;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/cors-00")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class BenchmarkTest03021 {
    private final FakeUserRepository users;

    public BenchmarkTest03021(FakeUserRepository users) {
        this.users = users;
    }

    @GetMapping("/BenchmarkTest03021/profile")
    public SupportModels.Account currentProfile() {
        return users.currentAccount();
    }
}
