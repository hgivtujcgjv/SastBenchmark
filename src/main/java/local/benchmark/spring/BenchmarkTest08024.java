package local.benchmark.spring;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.InitBinder;

 @RestController
 @RequestMapping("/benchmark/databinder")
 public class BenchmarkTest08024 {
     public static class Account {
         private String name;
         private boolean admin;
         public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean v) { this.admin = v; }
}

     @InitBinder
     void init(WebDataBinder binder) {
         binder.setAllowedFields("name");
     }

     @PostMapping("/BenchmarkTest08024")
     public String update(@ModelAttribute Account account) {
         return account.isAdmin() ? "granted" : "ok";
     }
 }
