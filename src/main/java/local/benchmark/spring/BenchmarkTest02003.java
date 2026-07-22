package local.benchmark.spring;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/databinder-00")
public class BenchmarkTest02003 {
    private final FakeUserRepository userRepository;

    public BenchmarkTest02003(FakeUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/BenchmarkTest02003")
    public SupportModels.UserProfile updateNestedProfile(@ModelAttribute SupportModels.UserProfile profile) {
        return userRepository.save(profile);
    }
}
