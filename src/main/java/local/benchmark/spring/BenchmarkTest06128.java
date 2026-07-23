package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-sqlinj2")
 public class BenchmarkTest06128 {
     @jakarta.persistence.PersistenceContext
private jakarta.persistence.EntityManager em;

@GetMapping("/BenchmarkTest06128")
     public String run(@RequestParam String p) throws Exception {
         return em.createQuery("SELECT u FROM User u WHERE u.name = :n")
            .setParameter("n", p).getResultList().toString();
     }
 }
