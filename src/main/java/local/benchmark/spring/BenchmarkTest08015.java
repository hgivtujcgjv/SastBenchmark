package local.benchmark.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class BenchmarkTest08015 {
    @Bean
    SecurityFilterChain BenchmarkTest08015Chain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.csrfTokenRepository(
                CookieCsrfTokenRepository.withHttpOnlyFalse()));
        return http.build();
    }
}
