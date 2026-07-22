package local.benchmark.spring;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/databinder-00")
public class BenchmarkTest02002 {
    private final FakeUserRepository userRepository;

    public BenchmarkTest02002(FakeUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/BenchmarkTest02002")
    public SupportModels.Account replaceAccount(@ModelAttribute SupportModels.Account account) {
        return userRepository.save(account);
    }
}
