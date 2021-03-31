package cn.fan.springbootkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    // 消费监听
    @KafkaListener(topics = {"test_topic"})
    public void onMessage1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }



    /**注意：topics和topicPartitions不能同时使用；
     * @Title 指定topic、partition、offset消费
     * @Description 同时监听topic1和topic2，监听topic1的0号分区、topic2的 "0号和1号" 分区，指向1号分区的offset初始值为8
     * @Date 2020/3/22 13:38
     * @Param [record]
     * @return void
     **/
    @KafkaListener(id = "consumer1",groupId = "felix-group",topicPartitions = {
            @TopicPartition(topic = "topic1", partitions = { "0" }),
            @TopicPartition(topic = "topic2", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
    })
    public void onMessage2(ConsumerRecord<?, ?> record) {
        System.out.println("topic:"+record.topic()+"|partition:"+record.partition()+"|offset:"+record.offset()+"|value:"+record.value());
    }

    /**
     * 手动提交模式
     * #手动提交模式
     * spring.kafka.listener.ack-mode=manual
     * spring.kafka.consumer.enable-auto-commit=false
     * @param record
     * @param ack
     */
    @KafkaListener(topics = {"offsettest"})
    public void processMessage(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        try {
            System.out.printf("topic is %s, offset is %d,partition is %s, value is %s \n", record.topic(), record.offset(),record.partition(), record.value());
            // 手动提交offset
            ack.acknowledge();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}