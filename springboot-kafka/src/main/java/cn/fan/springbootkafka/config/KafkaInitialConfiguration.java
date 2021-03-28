package cn.fan.springbootkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始话主题的分区和副本  直接发送默认只有一个分区 无副本
 */
@Configuration
public class KafkaInitialConfiguration {
    // 创建一个名为testtopic的Topic并设置分区数为8，分区副本数为2
//    @Bean
//    public NewTopic initialTopic() {
//        return new NewTopic("test_topic",8, (short) 2 );
//    }
//     // 如果要修改分区数，只需修改配置值重启项目即可
//    // 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
//    @Bean
//    public NewTopic updateTopic() {
//        return new NewTopic("test_topic",10, (short) 2 );
//    }
}