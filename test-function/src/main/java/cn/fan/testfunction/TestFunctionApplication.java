package cn.fan.testfunction;

import cn.fan.testfunction.service.Van;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TestFunctionApplication  implements CommandLineRunner {
    @Autowired
    private Van van;

    public static void main(String[] args) {
        SpringApplication.run(TestFunctionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        van.fight();
    }
}
