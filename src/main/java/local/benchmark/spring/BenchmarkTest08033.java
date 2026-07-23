package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/ldapi")
 public class BenchmarkTest08033 {
     private javax.naming.directory.DirContext ctx() throws Exception {
    java.util.Hashtable<String, Object> env = new java.util.Hashtable<>();
    env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(javax.naming.Context.PROVIDER_URL, "ldap://localhost:389");
    return new javax.naming.directory.InitialDirContext(env);
}

@GetMapping("/BenchmarkTest08033")
     public String lookup(@RequestParam String cn) throws Exception {
         return ctx().lookup("cn=" + cn + ",ou=people").toString();
     }
 }
