package local.benchmark.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class BenchmarkTest02007 {
    @Bean
    RouterFunction<ServerResponse> BenchmarkTest02007Route() {
        String baseDirectory = System.getProperty("user.home") + "/spring-benchmark-static/";
        return RouterFunctions.resources(
                "/benchmark/router-00/BenchmarkTest02007/files/**",
                new FileSystemResource(baseDirectory));
    }
}
