package cn.fan.springboot_rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**rabbitmq_delayed_message_exchange插件 rabbitmq需要安装 版本一定要一样
 * 延时队列实现（使用的是插件）  还可以采用死信队列实现
 */
@Configuration
public class DelayQueueConfig {
//这里要特别注意的是，使用的是CustomExchange,不是DirectExchange，另外CustomExchange的类型必须是x-delayed-message。
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("test_exchange", "x-delayed-message",true, false,args);
    }

    @Bean
    public Queue queue() {
        Queue queue = new Queue("test_queue_1", true);
        return queue;
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(delayExchange()).with("test_queue_1").noargs();
    }
}