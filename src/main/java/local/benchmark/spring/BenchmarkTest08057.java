package local.benchmark.spring;

import java.util.Set;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/springdata")
 public class BenchmarkTest08057 {
     @jakarta.persistence.PersistenceContext
private jakarta.persistence.EntityManager em;

private static final Set<String> SORTABLE = Set.of("name", "email", "createdAt");

     @GetMapping("/BenchmarkTest08057")
     public String sort(@RequestParam String order) {
         if (!SORTABLE.contains(order)) {
             return "rejected";
         }
         return em.createQuery("SELECT u FROM User u ORDER BY u." + order)
                 .getResultList().toString();
     }
 }
