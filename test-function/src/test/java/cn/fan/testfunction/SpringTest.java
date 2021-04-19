package cn.fan.testfunction;

import cn.fan.testfunction.config.AopBeanConfig;
import cn.fan.testfunction.config.SpringTestConfig;
import cn.fan.testfunction.service.impl.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/19 11:02
 */
public class SpringTest {

    @Test
    public void testSpring(){

        ApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringTestConfig.class);
        UserService userService = annotationConfigApplicationContext.getBean("userService", UserService.class);
        System.out.println(userService);
//        Class<?> user = Class.forName("cn.fan.testfunction.web.TestColntroller");
//
//        Method setService = user.getMethod("setService", TestService.class);
//       setService.invoke(user, )
//        System.out.println("ss");
    }
}
