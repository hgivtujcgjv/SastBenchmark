package local.benchmark.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BenchmarkTest06010 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            Runtime.getRuntime().exec("echo " + args[0]);
        }
    }
}
