package cn.fan.testfunction.config;

import cn.fan.testfunction.service.Fighter;
import cn.fan.testfunction.service.impl.Billy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/16 14:13
 */
@Configuration
public class AopBeanConfig {

    @Bean
    public Fighter fighter(){
        return new Billy();
    }
}
