package local.benchmark.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class BenchmarkTest02006 {
    @Bean
    RouterFunction<ServerResponse> BenchmarkTest02006Route() {
        return RouterFunctions.resources(
                "/benchmark/router-00/BenchmarkTest02006/files/**",
                new FileSystemResource("/tmp/spring-benchmark-static/"));
    }
}
