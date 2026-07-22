package local.benchmark.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class BenchmarkTest02009 {
    @Bean
    RouterFunction<ServerResponse> BenchmarkTest02009Route() {
        return RouterFunctions.resources(
                "/benchmark/router-00/BenchmarkTest02009/files/**",
                new ClassPathResource("public/"));
    }
}
