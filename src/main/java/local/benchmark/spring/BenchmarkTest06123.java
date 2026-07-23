package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-sqlinj2")
 public class BenchmarkTest06123 {
     @jakarta.persistence.PersistenceContext
private jakarta.persistence.EntityManager em;

@GetMapping("/BenchmarkTest06123")
     public String run(@RequestParam String p) throws Exception {
         return em.createNativeQuery("SELECT id FROM users WHERE name = '" + p + "'")
            .getResultList().toString();
     }
 }
