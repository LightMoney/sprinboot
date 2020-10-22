package cn.fan.springboot_rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
 
/**
 * @Author :
 * @CreateTime : 2019/9/3
 * @Description :
 * 目前回调存在ConfirmCallback和ReturnCallback两者。他们的区别在于
 *
 * 如果消息没有到exchange,则ConfirmCallback回调,ack=false,
 * 如果消息到达exchange,则ConfirmCallback回调,ack=true
 * exchange到queue成功,则不回调ReturnCallback
 **/
@Slf4j
@Configuration
public class RabbitConfig {
 
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback( new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
               log.info("ConfirmCallback:     "+"相关数据："+correlationData);
               log.info("ConfirmCallback:     "+"确认情况："+ack);
               log.info("ConfirmCallback:     "+"原因："+cause);
                if (ack){
                    log.info("消息已发送到mq，可进行相关操作");
                }
            }
        });
 
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
               log.info("ReturnCallback:     "+"消息："+message);
               log.info("ReturnCallback:     "+"回应码："+replyCode);
               log.info("ReturnCallback:     "+"回应信息："+replyText);
               log.info("ReturnCallback:     "+"交换机："+exchange);
               log.info("ReturnCallback:     "+"路由键："+routingKey);
            }
        });
        return rabbitTemplate;
    }
}