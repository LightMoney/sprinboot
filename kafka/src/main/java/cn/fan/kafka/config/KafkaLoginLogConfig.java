package cn.fan.kafka.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 读取其他配置文件
 * 当主配置文件太多
 */

@Configuration
@PropertySource("classpath:kafka-login.properties")  //读取外部注入配置文件
@Data
public class KafkaLoginLogConfig {
//    未指定外部文件就读的是默认配置文件
    @Value("${kafka.login.log.topic}")
    private String topic;

    @Value("${kafka.login.log.push}")
    private boolean push;

}
