package cn.fan.listener;

import cn.fan.server.HelloNettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 服务启动类监听 服务启动时就开始监听对应绑定的接口
 */
@Component
public class ServierListener implements CommandLineRunner {


    @Autowired
    private HelloNettyServer server;

    @Override
    public void run(String... args) throws Exception {
        new Thread(()->server.start()).start();
    }
}
