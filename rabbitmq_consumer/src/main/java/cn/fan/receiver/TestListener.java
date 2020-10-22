package cn.fan.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
/**
 * @@RabbitListener@RabbitListener 和 @RabbitHandler结合使用，不同类型的消息使用不同的方法来处理。
 * 实质是根据消息体的body类型来判断
 */
@Slf4j
@Component
@RabbitListener(queues = {"topic.man","topic.woman"})
public class TestListener {

    @RabbitHandler
    public void handler1(String msg) {
       log.info("1:===="+msg);
    }

//    @RabbitHandler
//    public void handler2(Map msg,Message message) {
//
//        log.info("2:===="+msg);
//        log.info(message.toString());
//    }

    @RabbitHandler
    public void handler3(Message msg) {
        log.info("3:===="+msg);
    }

}