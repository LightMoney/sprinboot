package cn.fan.receiver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * Auth Mr.fan
 * Date 2020/1/8 10:05
 *
 * 可以直接接受对应参数，直接写在接受方法参数中，但要注意序列化问题（看下是否自己设置了containerFactory中有自定义序列化）
 **/
@Slf4j
@Component
public class ReceiverListener {
    //@RabbitListener该注解默认自动应答 可以通过添加自定义的containerFactory（如果设定了自定义序列化方式，要注意可能无法直接使用发送类型的参数接收消息）
    @RabbitListener(queues = "topic.man",containerFactory = "rabbitListenerContainerFactory1")
    public void topicManReceiver(Map map, Message message, Channel channel){

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("TopicManReceiver消费者收到消息  : " + message.toString());

        try {
            channel.basicAck(deliveryTag,false);
//            channel.basicNack(deliveryTag,true,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @RabbitListener(queues = "topic.woman")
//    public void topicTotalReciver(Message message){
//        System.out.println("TopicTotalReceiver消费者收到消息  : " + message.toString());
//        System.out.println("TopicTotalReceiver消费者收到消息  : " + message.getMessageProperties().getDeliveryTag());
//    }

    @RabbitListener(queues = "queue-rabbit-springboot-advance")
    public  void  deadReceiver(String receiveMessage,Message message , Channel channel){
        try {
            // 手动签收
            log.info("接收到消息:[{}]", receiveMessage);
            if (new Random().nextInt(10) < 5) {
                log.warn("拒绝一条信息:[{}]，此消息将会由死信交换器进行路由.", receiveMessage);
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } else {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            log.info("接收到消息之后的处理发生异常.", e);
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e1) {
                log.error("签收异常.", e1);
            }
        }

    }
}
