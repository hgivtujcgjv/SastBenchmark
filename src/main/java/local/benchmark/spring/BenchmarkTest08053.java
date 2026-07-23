package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/springdata")
 public class BenchmarkTest08053 {
     @jakarta.persistence.PersistenceContext
private jakarta.persistence.EntityManager em;

@GetMapping("/BenchmarkTest08053")
     public String nativeq(@RequestParam String name) {
         return em.createNativeQuery(
                 "SELECT * FROM users WHERE name = '" + name + "'")
                 .getResultList().toString();
     }
 }
