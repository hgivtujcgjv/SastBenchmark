package local.benchmark.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BenchmarkTest03025 implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/benchmark/cors-00/BenchmarkTest03025/**")
                .allowedOrigins("https://portal.example.test")
                .allowedHeaders("Content-Type")
                .exposedHeaders("X-Request-Id")
                .allowCredentials(false);
    }
}
