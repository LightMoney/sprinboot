package cn.fan;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Auth Mr.fan
 * Date 2019/12/31 10:01
 **/
@SpringBootApplication
@MapperScan("cn.fan.dao")
public class ApplicationSecurityoauth {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSecurityoauth.class, args);
    }



}