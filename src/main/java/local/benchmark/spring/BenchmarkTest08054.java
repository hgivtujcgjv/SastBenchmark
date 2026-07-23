package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/springdata")
 public class BenchmarkTest08054 {
     @jakarta.persistence.PersistenceContext
private jakarta.persistence.EntityManager em;

@GetMapping("/BenchmarkTest08054")
     public String search(@RequestParam String table, @RequestParam String val) {
         return em.createNativeQuery(
                 "SELECT * FROM " + table + " WHERE name = '" + val + "'")
                 .getResultList().toString();
     }
 }
