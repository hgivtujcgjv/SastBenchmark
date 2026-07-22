package local.benchmark.spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/sqlinj-00")
public class BenchmarkTest03002 {
    private final JdbcTemplate jdbcTemplate;

    public BenchmarkTest03002(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/BenchmarkTest03002")
    public String lookupEmail(@RequestParam String id) {
        return jdbcTemplate.queryForObject("SELECT email FROM accounts WHERE id = '" + id + "'", String.class);
    }
}
