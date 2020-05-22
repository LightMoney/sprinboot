package cn.fan.config;

import cn.fan.receiver.DirectReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : fgy
 * @CreateTime : 2019/9/4
 * @Description :这种写法可以定制个别为自动应答
 **/
@Configuration
public class MessageListenerConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private DirectReceiver directReceiver;//Direct消息接收处理类
    //    @Autowired
//    FanoutReceiverA fanoutReceiverA;//Fanout消息接收处理类A
    @Autowired
    DirectRabbitConfig directRabbitConfig;

    //    @Autowired
//    FanoutRabbitConfig fanoutRabbitConfig;
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setQueues(directRabbitConfig.TestDirectQueue());
        container.setMessageListener(directReceiver);

//        container.addQueues(fanoutRabbitConfig.queueA());
//        container.setMessageListener(fanoutReceiverA);
        return container;
    }

//    会有一些报错，在xml中添加如下配置
//    listener:
//    simple:
//    acknowledge-mode:manual
//    concurrency:1
//    max-concurrency:1
//    retry:
//    enabled:true
}