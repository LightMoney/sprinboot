package cn.fan.websocket.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/imserver/{userId}")
@Slf4j
public class WebSocketServer {
    //静态变量记录连接数，设计成线程安全的
    private static int onlineCount=0;
    //用来存放每个客户端对应的MyWebSocket对象
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap=new ConcurrentHashMap<>();
    //与客户的连接会话，使用它给用户发信息
    private Session session;
    //接收的用户id
    private String userId;

}
