package local.benchmark.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

@Configuration
public class BenchmarkTest06005 {
    @Bean
    RouterFunction<ServerResponse> BenchmarkTest06005Route() {
        return RouterFunctions.route()
                .GET("/benchmark/cmdi-entry/BenchmarkTest06005", request -> {
                    String host = request.param("host").orElse("");
                    Runtime.getRuntime().exec("echo " + host);
                    return ServerResponse.ok().body("ok");
                })
                .build();
    }
}
