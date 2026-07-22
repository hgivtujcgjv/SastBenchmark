package local.benchmark.spring;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/databinder-00")
public class BenchmarkTest02005 {
    private final FakeUserRepository userRepository;

    public BenchmarkTest02005(FakeUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @InitBinder
    public void allowOnlyProfileFields(WebDataBinder binder) {
        binder.setAllowedFields("displayName", "email");
    }

    @PostMapping("/BenchmarkTest02005")
    public SupportModels.Account updateWithAllowedFields(@ModelAttribute SupportModels.Account account) {
        return userRepository.save(account);
    }
}
