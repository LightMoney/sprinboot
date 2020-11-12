package cn.fan.swaggernew;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * http://localhost:端口号/swagger-ui/
 */
@MapperScan("cn.fan.swaggernew.dao")
@SpringBootApplication
@EnableOpenApi   //这个注解不写好像也可以
public class SwaggerNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerNewApplication.class, args);
    }

}
