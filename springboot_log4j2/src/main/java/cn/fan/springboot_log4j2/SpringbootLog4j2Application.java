package cn.fan.springboot_log4j2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class SpringbootLog4j2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLog4j2Application.class, args);
        log.error("Something else is wrong here");
    }

}
