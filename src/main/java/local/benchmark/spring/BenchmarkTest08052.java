package local.benchmark.spring;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/benchmark/springdata")
public class BenchmarkTest08052 {
    private final UserRepo repo;
    public BenchmarkTest08052(UserRepo repo) { this.repo = repo; }

    interface UserRepo extends org.springframework.data.jpa.repository.JpaRepository<Object, Long> {}

    @GetMapping("/BenchmarkTest08052")
    public String list(@RequestParam String sortField) {
        return repo.findAll(Sort.by(Sort.Direction.ASC, sortField)).toString();
    }
}
