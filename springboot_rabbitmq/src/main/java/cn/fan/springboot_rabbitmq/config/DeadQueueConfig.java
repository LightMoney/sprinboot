package cn.fan.springboot_rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * 死信队列
 *
 * 队列中的消息在以下三种情况下会变成死信
 * （1）消息被拒绝（basic.reject 或者 basic.nack），并且requeue=false;
 * （2）消息的过期时间到期了；
 * （3）队列长度限制超过了。
 * 当队列中的消息成为死信以后，如果队列设置了DLX那么消息会被发送到DLX。通
 * 过x-dead-letter-exchange设置DLX，通过这个x-dead-letter-routing-key设置消息发送到DLX所用的routing-key，如果不设置默认使用消息本身的routing-key.
 */
@Configuration
public class DeadQueueConfig {
    /**
     * 申明队列
     *
     * @return
     */
    @Bean
    public Queue queue1() {
        Map<String, Object> arguments = new HashMap<>(4);
        // 申明死信交换器
        arguments.put("x-dead-letter-exchange", "exchange-dlx");
        return new Queue("queue-rabbit-springboot-advance", true, false, false, arguments);
    }

    /**
     * 没有路由到的消息将进入此队列
     *
     * @return
     */
    @Bean
    public Queue unRouteQueue() {
        return new Queue("queue-unroute");
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue dlxQueue() {
        return new Queue("dlx-queue");
    }

    /**
     * 申明交换器
     *
     * @return
     */
    @Bean
    public Exchange exchange1() {
        Map<String, Object> arguments = new HashMap<>(4);
        // 当发往exchange-rabbit-springboot-advance的消息,routingKey和bindingKey没有匹配上时，将会由exchange-unroute交换器进行处理
        arguments.put("alternate-exchange", "exchange-unroute");
        return new DirectExchange("exchange-rabbit-springboot-advance", true, false, arguments);
    }

    @Bean
    public FanoutExchange unRouteExchange() {
        // 此处的交换器的名字要和 exchange() 方法中 alternate-exchange 参数的值一致
        return new FanoutExchange("exchange-unroute");
    }

    /**
     * 申明死信交换器
     *
     * @return
     */
    @Bean
    public FanoutExchange dlxExchange() {
        return new FanoutExchange("exchange-dlx");
    }

    /**
     * 申明绑定
     *
     * @return
     */
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queue1()).to(exchange1()).with("product").noargs();
    }

    @Bean
    public Binding unRouteBinding() {
        return BindingBuilder.bind(unRouteQueue()).to(unRouteExchange());
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange());
    }

}
