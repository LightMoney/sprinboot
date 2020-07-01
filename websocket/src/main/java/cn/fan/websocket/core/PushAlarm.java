package cn.fan.websocket.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 实时推送
 *
 * @author Siona
 * @date 2020/4/9 10:12
 **/
@Slf4j
@Component  // 被Spring容器管理
@Order(1)   // 如果多个自定义ApplicationRunner，用来表明执行顺序
public class PushAlarm implements ApplicationRunner {   // 服务启动后自动加载该类

//    @Autowired
//    GasSupport gasSupport;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("------------->" + "项目启动，now =" + new Date());
        this.myTimer();
    }

    public void myTimer() {

        String userId = null; // userId 为空时，会推送给连接此 WebSocket 的所有人

        Runnable runnable1 = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
//                    String message = gasSupport.GetWasteGasRealData(""); // 第三方接口返回数据
                    String message = "good";
                    WebSocketServer.sendInfo(message, userId); // 推送
                    Thread.sleep(5000);
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
//                    String message = gasSupport.GetWasteWaterRealData(""); // 第三方接口返回数据
                    String message = "test";
                    WebSocketServer.sendInfo(message, userId); // 推送
                    Thread.sleep(5000);
                }
            }
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

    }

}

