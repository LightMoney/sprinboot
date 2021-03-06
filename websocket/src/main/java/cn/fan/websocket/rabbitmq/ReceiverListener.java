package cn.fan.websocket.rabbitmq;

import cn.fan.websocket.core.WebSocketServer;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

/**
 * Auth Mr.fan
 * Date 2020/1/8 10:05
 **/
@Slf4j
@Component
public class ReceiverListener {
    @RabbitListener(queues = "topic.man")
    public void topicManReceiver(Message message) throws IOException {
        System.out.println("TopicManReceiver消费者收到消息  : " + message.toString());
        WebSocketServer.sendInfo(message.toString(), null);
    }

    @RabbitListener(queues = "topic.woman")
    public void topicTotalReciver(Message message) {
        System.out.println("TopicTotalReceiver消费者收到消息  : " + message.toString());
    }

    @RabbitListener(queues = "queue-rabbit-springboot-advance")
    public void deadReceiver(String receiveMessage, Message message, Channel channel) {
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
