package local.benchmark.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BenchmarkTest02019 {
    @Bean
    @Order(219)
    SecurityFilterChain BenchmarkTest02019Filter(HttpSecurity http) throws Exception {
        http.securityMatcher("/benchmark/csrf-00/BenchmarkTest02019/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
