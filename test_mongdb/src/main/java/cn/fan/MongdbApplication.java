package cn.fan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class MongdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongdbApplication.class, args);
    }

}
