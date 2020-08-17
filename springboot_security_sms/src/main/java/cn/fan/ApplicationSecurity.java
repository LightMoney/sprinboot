package cn.fan;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Auth Mr.fan
 * Date 2019/12/31 10:01
 **/
@SpringBootApplication
public class ApplicationSecurity {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSecurity.class, args);
    }

    /**
     * 注入验证码servlet
     */
//    @Bean
//    public ServletRegistrationBean indexServletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new VerifyServlet());
//        registration.addUrlMappings("/getVerifyCode");
//        return registration;
//    }

}