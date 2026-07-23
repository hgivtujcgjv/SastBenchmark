package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/databinder")
 public class BenchmarkTest08021 {
     public static class Account {
         private String name;
         private boolean admin;
         public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean v) { this.admin = v; }
}

     @PostMapping("/BenchmarkTest08021")
     public String update(@ModelAttribute Account account) {
         return account.isAdmin() ? "granted admin" : "ok";
     }
 }
