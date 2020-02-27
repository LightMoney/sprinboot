package cn.fan.receiver;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Auth Mr.fan
 * Date 2020/1/8 10:05
 **/
@Component
public class ReceiverListener {
    @RabbitListener(queues = "topic.man")
    public void topicManReceiver(Message message){
        System.out.println("TopicManReceiver消费者收到消息  : " + message.toString());
    }
    @RabbitListener(queues = "topic.woman")
    public void topicTotalReciver(Message message){
        System.out.println("TopicTotalReceiver消费者收到消息  : " + message.toString());
    }
}
