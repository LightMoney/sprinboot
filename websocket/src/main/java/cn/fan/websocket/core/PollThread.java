//package cn.fan.websocket.core;
//
//import com.alibaba.fastjson.JSON;
//import com.hthl.config.ApplicationContextProvider;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.Session;
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.LinkedBlockingQueue;
//
//@Component
//@Slf4j
//public class PollThread implements Runnable {
//    // 缓存消息
//    public static BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();
//
//
//    private static StringRedisTemplate redis1;
//
//    static {
//        redis1 = (StringRedisTemplate) ApplicationContextProvider.getBean("defaultStringRedisTemplate");
//    }
//    // 指定初始化容量，大小为2的幂，以减少扩容产生的性能消耗
//    // 缓存 session 连接
//    public static ConcurrentHashMap<Session, Integer> cachedSessions = new ConcurrentHashMap<>(256);
//
//    /**
//     * 添加消息到缓冲队列
//     *
//     * @param message
//     */
//
//    public static void addMessageToQueue(String message) {
//        messageQueue.offer(message);
//    }
//
//    /**
//     * 消费消息
//     *
//     * @return
//     */
//    private static String getMessageFromQueue() {
//        String item = messageQueue.poll();
//        if (item != null && !item.trim().equals("")) {
//            return item;
//        }
//        return null;
//    }
//
//    @Override
//    public void run() {
//        while (messageQueue.size() != 0) {
//            String message = getMessageFromQueue();
//            log.info("消息已被消费，当前消息数量为：" + messageQueue.size());
//            try {
//                sendMessage(message, cachedSessions);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            log.info("当前没消息");
//        }
////        if (messageQueue.size() != 0) {
////            String message = getMessageFromQueue();
////            log.info("消息已被消费，当前消息数量为：" + messageQueue.size());
////            try {
////                sendMessage(message, cachedSessions);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        } else {
////            log.info("当前没消息");
////        }
//    }
//
//    /**
//     * 主动发送消息给页面
//     *
//     * @param message
//     */
//    public void sendMessage(String message, ConcurrentHashMap<Session, Integer> cachedSessions) {
//        // 3. 使用Iterator遍历
////        if (message == null || cachedSessions.size() == 0) {
////            log.info("消息为空或者，当前用户为0，信息被抛掉");
////            return;
////        }
//        StringBuilder sb = new StringBuilder();
//        Map map = JSON.parseObject(message, Map.class);
//        Iterator<Map.Entry<Session, Integer>> it = cachedSessions.entrySet().iterator();
//        int messageEnterpriseId = Integer.parseInt(map.get("enterpriseId").toString());
//        String h = sb.append(messageEnterpriseId).append(":").append(map.get("equipNo")).append("*").toString();
//        Set<String> keys = redis1.keys(h);
//        if (keys.size() == 0) {
//            map.put("type", 0);
//        } else {
//            map.put("type", 1);
//        }
//        while (it.hasNext()) {
//            Map.Entry<Session, Integer> entry = it.next();
//            Session session = entry.getKey();
//            log.error(session.toString());
//            Integer enterpriseId = entry.getValue();
//            log.info(JSON.toJSONString(map));
//            if (enterpriseId == messageEnterpriseId) {
//                synchronized (session) {
//                    try {
//                        session.getBasicRemote().sendText(JSON.toJSONString(map));
//                        log.info("发送websocket消息成功");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 添加会话
//     *
//     * @param session：当前会话
//     * @param enterpriseId：企业id
//     */
//    public static void addCachedSessions(Session session, Integer enterpriseId) {
//        PollThread.cachedSessions.put(session, enterpriseId);
//    }
//
//    /**
//     * 添加会话
//     *
//     * @param session：当前会话
//     */
//    public static void removeCachedSessions(Session session) {
//        PollThread.cachedSessions.remove(session);
//    }
//}
