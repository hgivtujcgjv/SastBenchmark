package local.benchmark.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BenchmarkTest08013 {
    @Bean
    SecurityFilterChain BenchmarkTest08013Chain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/**"));
        return http.build();
    }
}
