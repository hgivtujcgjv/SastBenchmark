package local.benchmark.spring;

import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-sqlinj2")
 public class BenchmarkTest06126 {
     private final org.springframework.jdbc.core.JdbcTemplate jdbc;

public BenchmarkTest06126(org.springframework.jdbc.core.JdbcTemplate jdbc) {
    this.jdbc = jdbc;
}

@GetMapping("/BenchmarkTest06126")
     public String run(@RequestParam String p) throws Exception {
         return jdbc.queryForObject("SELECT email FROM users WHERE name = '" + p + "'",
            String.class);
     }
 }
