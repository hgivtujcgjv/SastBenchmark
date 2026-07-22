package local.benchmark.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BenchmarkTest02017 {
    @Bean
    @Order(217)
    SecurityFilterChain BenchmarkTest02017Filter(HttpSecurity http) throws Exception {
        http.securityMatcher("/benchmark/csrf-00/BenchmarkTest02017/**")
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
