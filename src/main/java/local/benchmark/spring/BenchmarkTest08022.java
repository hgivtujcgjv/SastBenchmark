package local.benchmark.spring;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/databinder")
 public class BenchmarkTest08022 {
     public static class Account {
         private String name;
         private boolean admin;
         public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean v) { this.admin = v; }
}

     @PostMapping("/BenchmarkTest08022")
     public String update(@RequestParam java.util.Map<String, String> params) {
         Account account = new Account();
         WebDataBinder binder = new WebDataBinder(account);
         binder.bind(new org.springframework.web.bind.ServletRequestParameterPropertyValues(
                 ((org.springframework.web.context.request.ServletRequestAttributes)
                  org.springframework.web.context.request.RequestContextHolder
                          .currentRequestAttributes()).getRequest()));
         return account.isAdmin() ? "granted" : "ok";
     }
 }
