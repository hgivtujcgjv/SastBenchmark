package local.benchmark.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

@Configuration
public class BenchmarkTest06016 {
    @Bean
    RouterFunction<ServerResponse> BenchmarkTest06016Route() {
        return RouterFunctions.route()
                .GET("/benchmark/cmdi-entry/BenchmarkTest06016", request -> {
                    new ProcessBuilder("echo", request.param("host").orElse("")).start();
                    return ServerResponse.ok().body("ok");
                })
                .build();
    }
}
