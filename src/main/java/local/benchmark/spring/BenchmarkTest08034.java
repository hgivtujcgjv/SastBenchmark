package local.benchmark.spring;

import javax.naming.directory.SearchControls;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/ldapi")
 public class BenchmarkTest08034 {
     private javax.naming.directory.DirContext ctx() throws Exception {
    java.util.Hashtable<String, Object> env = new java.util.Hashtable<>();
    env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(javax.naming.Context.PROVIDER_URL, "ldap://localhost:389");
    return new javax.naming.directory.InitialDirContext(env);
}

@GetMapping("/BenchmarkTest08034")
     public String search(@RequestParam String user) throws Exception {
         var results = ctx().search("ou=people", "(uid=0)",
                 new Object[]{user}, new SearchControls());
         return results.hasMore() ? "found" : "none";
     }
 }
