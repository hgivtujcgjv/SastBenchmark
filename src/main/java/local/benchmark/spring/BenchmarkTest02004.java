package local.benchmark.spring;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/databinder-00")
public class BenchmarkTest02004 {
    private final FakeUserRepository userRepository;

    public BenchmarkTest02004(FakeUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/BenchmarkTest02004")
    public SupportModels.Account updateWithDto(@ModelAttribute SupportModels.UpdateProfileRequest request) {
        SupportModels.Account account = userRepository.currentAccount();
        account.setDisplayName(request.getDisplayName());
        account.setEmail(request.getEmail());
        return userRepository.save(account);
    }
}
