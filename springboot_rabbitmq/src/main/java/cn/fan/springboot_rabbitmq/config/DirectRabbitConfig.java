package cn.fan.springboot_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auth Mr.fan
 * Date 2020/1/7 14:11
 **/
@Configuration
public class DirectRabbitConfig {
    //队列 起名
    @Bean
    public Queue TestDirectQueue(){
//        QueueBuilder.durable("TestDirectQueue").build();
        return new Queue("TestDirectQueue",true);//true 是否持久
    }
    //交换机 取名
    @Bean
    DirectExchange TestDirectExchange(){
//        ExchangeBuilder.directExchange("test").durable(true).build();

        return  new DirectExchange("TestDirectExchange");
    }
//
//    @Bean(EXCHANGE_TOPIC_INFORM_DATA)
//    public Exchange EXCHANGE_TOPIC_INFORM_DATA(){
//        //  durable(true) 交换机持久化,mq重启之后,交换机还在
//        return ExchangeBuilder.topicExchange(EXCHANGE_TOPIC_INFORM_DATA).durable(true).build();
//    }
    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }
    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

}
