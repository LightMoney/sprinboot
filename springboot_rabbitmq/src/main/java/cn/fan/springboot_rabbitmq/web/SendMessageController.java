package cn.fan.springboot_rabbitmq.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**使用convertAndSend方式发送消息，消息默认就是持久化的.
 * Auth Mr.fan
 * Date 2020/1/7 14:19
 *
 **/
@RestController
public class SendMessageController {
    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", correlationData.getId());
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);

//        rabbitTemplate.convertAndSend("topic.man",manMap,correlationData);
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
        return "ok";
    }


    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }

    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
//        map.put("messageId", messageId);
//        map.put("messageData", messageData);
//        map.put("createTime", createTime);
//        map.put("testTime",new Date());
//        map.put("ll",System.currentTimeMillis());
//        map.put("bigData",1234563.01);
//        rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
        //这里数据不做json转换，那就需要接受方解析然后再转换
//        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting", JSONObject.toJSONString(map) );
//        private String taskId;
//        @ApiModelProperty("当前时间")
//        private Long createTime;
//        @ApiModelProperty("格式化显示时间")
//        private String showTime;
//        @ApiModelProperty("企业id")
//        private Integer enterpriseId;
//        @ApiModelProperty("经度")
//        private Double longitude;
//        @ApiModelProperty("纬度")
//        private Double latitude;
//        @ApiModelProperty("mac值")
//        private String macAddress;
        map.put("taskId","tt123456789788");
        map.put("createTime",System.currentTimeMillis());
        map.put("enterpriseId",100032);
        map.put("longitude",124.4568888);
        map.put("latitude",124447.11111111111);
        map.put("macAddress","adsf:45sf:sdfs:45se");
        rabbitTemplate.convertAndSend("position_log_exchang","position_log_roukey",map);
        return "ok";
    }

    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    /**
     * 注意在发送的时候，必须加上一个header
     *
     * x-delay
     * 在这里我设置的延迟时间是3秒。
     * @param queueName
     * @param msg
     */
    @GetMapping("/delay")
    public void sendMsg(@RequestParam String queueName, @RequestParam String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("消息发送时间:"+sdf.format(new Date()));
        rabbitTemplate.convertAndSend("test_exchange", queueName, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay",3000);
                return message;
            }
        });
    }

    @GetMapping("/dead")
    public void  sendDead(){
        rabbitTemplate.convertAndSend("exchange-rabbit-springboot-advance","product","good");
        rabbitTemplate.convertAndSend("exchange-rabbit-springboot-advance","norProduct","good  bye");
    }
}
