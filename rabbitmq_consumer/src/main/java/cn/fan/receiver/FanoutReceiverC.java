package cn.fan.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 可以直接接受对应参数，直接写在接受方法参数中，但要注意序列化问题（看下是否自己设置了containerFactory中有自定义序列化）
 * @Author : JCccc
 * @CreateTime : 2019/9/3
 * @Description :
 **/
@Component
@RabbitListener(queues = "fanout.C",containerFactory = "rabbitListenerContainerFactory1")
public class FanoutReceiverC {

    @RabbitHandler
    public void process(Map testMessage) {

        System.out.println("FanoutReceiverC消费者收到消息  : " +testMessage.toString());
    }

}