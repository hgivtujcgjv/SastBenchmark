package local.benchmark.spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/sqlinj-00")
public class BenchmarkTest03001 {
    private final JdbcTemplate jdbcTemplate;

    public BenchmarkTest03001(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/BenchmarkTest03001")
    public String deactivateAccount(@RequestParam String name) {
        jdbcTemplate.execute("UPDATE accounts SET active = 0 WHERE name = '" + name + "'");
        return "deactivated";
    }
}
