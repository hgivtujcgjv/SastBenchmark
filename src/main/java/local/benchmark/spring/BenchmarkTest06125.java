package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-sqlinj2")
 public class BenchmarkTest06125 {
     private final org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate npjdbc;

public BenchmarkTest06125(org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate npjdbc) {
    this.npjdbc = npjdbc;
}

@GetMapping("/BenchmarkTest06125")
     public String run(@RequestParam String p) throws Exception {
         return npjdbc.queryForList("SELECT id FROM users WHERE name = '" + p + "'",
            new java.util.HashMap<String, Object>()).toString();
     }
 }
