package cn.fan.testfunction.config;

import cn.fan.testfunction.service.Fighter;
import cn.fan.testfunction.service.impl.Babana;
import cn.fan.testfunction.service.impl.Billy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VanConfig {
    @Bean
//    @ConditionalOnBean(Billy.class)
    public Fighter fighter(){
        return new Billy();
    }

    @Bean
//    @ConditionalOnMissingBean
    public Fighter fighter2(){
        return new Babana();
    }
}