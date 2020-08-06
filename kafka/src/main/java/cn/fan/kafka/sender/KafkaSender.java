package cn.fan.kafka.sender;


import cn.fan.kafka.config.KafkaLoginLogConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: xyd
 * @Date: 2019/7/4 17:03
 * @Description:
 */
@Component("loginKafkaSender")
@Slf4j
public class KafkaSender {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaLoginLogConfig loginLogConfig;


    @Async("loginTaskExecutor")
    public void sendData(String topic, Object data) {
        log.info("推送线程名称：{}", Thread.currentThread().getName());
        if (!loginLogConfig.isPush()) {
            log.debug("=========未开启推送kafka 登录日志==========");
            return;
        }
        ListenableFuture result = kafkaTemplate.send(topic, System.currentTimeMillis() + "", data);
        SuccessCallback successCallback = new SuccessCallback() {
            @Override
            public void onSuccess(Object result) {
                log.info("======================== kafka 采集登录日志成功 ========================");
            }
        };
        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("kafka 采集登录日志失败", ex);
            }
        };
        result.addCallback(successCallback, failureCallback);
    }

    @EnableAsync
    @Configuration
    class TaskPoolConfig {
        @Bean("loginTaskExecutor")
        public Executor taskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(10);
            executor.setMaxPoolSize(20);
            executor.setQueueCapacity(200);
            executor.setKeepAliveSeconds(60);
            executor.setThreadNamePrefix("loginTaskExecutor-");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
            executor.setWaitForTasksToCompleteOnShutdown(true);
            executor.setAwaitTerminationSeconds(60);
            return executor;
        }
    }
}