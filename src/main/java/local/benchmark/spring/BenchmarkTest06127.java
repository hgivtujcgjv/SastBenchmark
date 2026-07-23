package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-sqlinj2")
 public class BenchmarkTest06127 {
     private final org.springframework.jdbc.core.JdbcTemplate jdbc;

public BenchmarkTest06127(org.springframework.jdbc.core.JdbcTemplate jdbc) {
    this.jdbc = jdbc;
}

@GetMapping("/BenchmarkTest06127")
     public String run(@RequestParam String p) throws Exception {
         return jdbc.queryForList("SELECT id FROM users WHERE name = ?", p).toString();
     }
 }
