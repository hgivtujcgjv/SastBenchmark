package local.benchmark.spring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/sqlinj-00")
public class BenchmarkTest03003 {
    private final EntityManager entityManager;

    public BenchmarkTest03003(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/BenchmarkTest03003")
    public List<?> searchUsers(@RequestParam String name) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = '" + name + "'");
        return query.getResultList();
    }
}
