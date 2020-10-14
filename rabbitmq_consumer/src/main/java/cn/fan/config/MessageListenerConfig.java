package cn.fan.config;

import cn.fan.receiver.DirectReceiver;
import cn.fan.receiver.FanoutReceiverA;
import cn.fan.receiver.ReceiverListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : fgy
 * @CreateTime : 2019/9/4
 * @Description :这种写法可以定制个别为自动应答 ，但需要就收类实现 ChannelAwareMessageListener 接口 否者报错
 **/
@Configuration
public class MessageListenerConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private DirectReceiver directReceiver;//Direct消息接收处理类

    @Autowired
    DirectRabbitConfig directRabbitConfig;




    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setQueues(directRabbitConfig.TestDirectQueue());
        container.setMessageListener(directReceiver);


        return container;
    }

    /**
     * 下面的配置会使所有都变为手动应答(因为默认的containerfacory就是这个名字rabbitListenerContainerFactory)
     * 要部分手动要给bean 重新命名
     */
    @Bean("rabbitListenerContainerFactory1")
    @ConditionalOnClass
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(CachingConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
//不指定则使用默认配置配置文件中的rabbitmq地址
//    @Bean
//    public ConnectionFactory connectionFactory(){
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setUri("amqp://zhihao.miao:123456@192.168.1.131:5672");
//        return factory;
//    }

//    会有一些报错，在xml中添加如下配置
//    listener:
//    simple:
//    acknowledge-mode:manual
//    concurrency:1
//    max-concurrency:1
//    retry:
//    enabled:true
//    max-attempts: 5  #默认三次
//    这里只是消费者监听的一种写法，也可如ReceiverListener 一样写，
//    就不用实现ChannelAwareMessageListener，也不用写MessageListenerConfig这个配置类，yml中配置文件如上


//    @RabbitListener(
//            bindings = @QueueBinding(
//                    value = @Queue(value = "myQueue1"),
//                    exchange = @Exchange(value = "myExchange1"),
//                    key = "routingKey1"
//            ),
//            concurrency =  "10"
//    )


}