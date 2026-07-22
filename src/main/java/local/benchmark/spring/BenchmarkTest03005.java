package local.benchmark.spring;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benchmark/sqlinj-00")
public class BenchmarkTest03005 {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BenchmarkTest03005(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @GetMapping("/BenchmarkTest03005")
    public String lookupEmail(@RequestParam String id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject("SELECT email FROM accounts WHERE id = :id", parameters, String.class);
    }
}
