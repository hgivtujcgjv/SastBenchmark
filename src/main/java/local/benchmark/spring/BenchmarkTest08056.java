package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/springdata")
 public class BenchmarkTest08056 {
     @jakarta.persistence.PersistenceContext
private jakarta.persistence.EntityManager em;

@GetMapping("/BenchmarkTest08056")
     public String search(@RequestParam String name) {
         return em.createQuery("SELECT u FROM User u WHERE u.name = :n")
                 .setParameter("n", name).getResultList().toString();
     }
 }
