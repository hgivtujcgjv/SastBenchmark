package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-sqlinj2")
 public class BenchmarkTest06121 {
     private final org.springframework.jdbc.core.JdbcTemplate jdbc;

public BenchmarkTest06121(org.springframework.jdbc.core.JdbcTemplate jdbc) {
    this.jdbc = jdbc;
}

@GetMapping("/BenchmarkTest06121")
     public String run(@RequestParam String p) throws Exception {
         jdbc.execute("UPDATE accounts SET active = 0 WHERE name = '" + p + "'");
    return "ok";
     }
 }
