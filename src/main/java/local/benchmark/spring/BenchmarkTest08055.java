package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/springdata")
 public class BenchmarkTest08055 {
     @jakarta.persistence.PersistenceContext
private jakarta.persistence.EntityManager em;

@GetMapping("/BenchmarkTest08055")
     public String like(@RequestParam String q) {
         return em.createQuery(
                 "SELECT u FROM User u WHERE u.name LIKE '%" + q + "%'")
                 .getResultList().toString();
     }
 }
