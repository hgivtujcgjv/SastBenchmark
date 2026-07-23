package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/springdata")
 public class BenchmarkTest08051 {
     @jakarta.persistence.PersistenceContext
private jakarta.persistence.EntityManager em;

@GetMapping("/BenchmarkTest08051")
     public String sort(@RequestParam String order) {
         return em.createQuery("SELECT u FROM User u ORDER BY u." + order)
                 .getResultList().toString();
     }
 }
