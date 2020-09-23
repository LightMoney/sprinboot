package cn.fan.testfunction;

import cn.fan.testfunction.service.Van;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CommandLineRunner 在项目启动后执行run中方法
 * # 如果有多个类实现CommandLineRunner接口，如何保证顺序
 * > SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法，如果需要按照一定的顺序去执行，那么就需要在实体类上使用一个@Order注解（或者实现Order接口）来表明顺序
 */
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
