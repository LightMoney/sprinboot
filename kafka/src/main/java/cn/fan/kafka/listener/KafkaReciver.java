package cn.fan.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaReciver {

    @KafkaListener(topics = "TEST_TEST")
    public void getKafkaMessage(ConsumerRecord<?, ?> record) {
        System.out.println(record.value().toString());
    }
}
