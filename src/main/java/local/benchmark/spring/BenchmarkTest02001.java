package local.benchmark.spring;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/databinder-00")
public class BenchmarkTest02001 {
    private final FakeUserRepository userRepository;

    public BenchmarkTest02001(FakeUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/BenchmarkTest02001")
    public SupportModels.Account updateProfile(SupportModels.Account account) {
        return userRepository.save(account);
    }
}
