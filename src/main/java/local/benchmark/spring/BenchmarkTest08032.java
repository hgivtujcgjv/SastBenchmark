package local.benchmark.spring;

import javax.naming.directory.SearchControls;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/ldapi")
 public class BenchmarkTest08032 {
     private javax.naming.directory.DirContext ctx() throws Exception {
    java.util.Hashtable<String, Object> env = new java.util.Hashtable<>();
    env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(javax.naming.Context.PROVIDER_URL, "ldap://localhost:389");
    return new javax.naming.directory.InitialDirContext(env);
}

@GetMapping("/BenchmarkTest08032")
     public String search(@RequestParam String user, @RequestParam String dept) throws Exception {
         String filter = "(&(uid=" + user + ")(department=" + dept + "))";
         var results = ctx().search("ou=people", filter, new SearchControls());
         return results.hasMore() ? "found" : "none";
     }
 }
