package cn.fan.receiver;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
//public class DirectReceiver {
//
//    @RabbitHandler
//    public void process(Map testMessage) {
//        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
//
//    }
//}

//message也可用spring
import cn.fan.model.ReModel;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 可以直接接受对应参数，直接写在接受方法参数中，但要注意序列化问题（看下是否自己设置了containerFactory中有自定义序列化）
 * @Author : JCccc
 * @CreateTime : 2019/9/3
 * @Description :
 **/
@Slf4j
@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiver implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
//            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim());
            //msgArray中数据若不是json需要转换为json结构（也可接收二进制流）
            ReModel reModel = JSONObject.parseObject(msgArray[1], new TypeReference<ReModel>(){});


            log.info("=========="+reModel);
            //也可以直接转成json或对象操作
//            String messageId=msgMap.get("messageId");
//            String messageData=msgMap.get("messageData");
//            String createTime=msgMap.get("createTime");
//           log.info("messageId:"+messageId+"  messageData:"+messageData+"  createTime:"+createTime);
            //channel.basicAck()
            //deliveryTag:该消息的index
            //multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
            channel.basicAck(deliveryTag, true);

            //basicNack(long deliveryTag, boolean multiple, boolean requeue)
            //deliveryTag:该消息的index
            //multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
            //requeue：被拒绝的是否重新入队列
//			channel.basicReject(deliveryTag, true);//为true会重新放回队列
        } catch (Exception e) {
    // deliveryTag:该消息的index
    //requeue：被拒绝的是否重新入队列
            channel.basicReject(deliveryTag, false);
            //channel.basicNack 与 channel.basicReject 的区别在于basicNack可以拒绝多条消息，而basicReject一次只能拒绝一条消息
            e.printStackTrace();
        }
    }

    //{key=value,key=value,key=value} 格式转换成map
    private Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}