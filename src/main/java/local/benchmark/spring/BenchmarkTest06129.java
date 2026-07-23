package local.benchmark.spring;

import java.util.Set;
import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/benchmark/c-sqlinj2")
 public class BenchmarkTest06129 {
     private static final Set<String> COLUMNS = Set.of("name", "email");

private final org.springframework.jdbc.core.JdbcTemplate jdbc;

public BenchmarkTest06129(org.springframework.jdbc.core.JdbcTemplate jdbc) {
    this.jdbc = jdbc;
}

@GetMapping("/BenchmarkTest06129")
     public String run(@RequestParam String p) throws Exception {
         if (!COLUMNS.contains(p)) {
        return "rejected";
    }
    return jdbc.queryForList("SELECT " + p + " FROM users").toString();
     }
 }
