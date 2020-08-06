//package cn.fan.websocket.core;
//
//import com.alibaba.fastjson.JSON;
//import com.hthl.config.ApplicationContextProvider;
//import com.hthl.config.JwtKey;
//import com.hthl.framework.domain.warn.VoMapData;
//import com.hthl.framework.domain.warn.VoMapEquipData;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.*;
//
///**
// * web socket 推送业务
// *
// * @author 刘青友
// */
//@Component
//@ServerEndpoint(value = "/realData")
//@Slf4j
//public class RealDataTransPort {
//
//    static StringRedisTemplate template6;
//    static StringRedisTemplate template1;
//
//    static {
//        template6 = (StringRedisTemplate) ApplicationContextProvider.getBean("articleStringRedisTemplate");
//        template1 = (StringRedisTemplate) ApplicationContextProvider.getBean("defaultStringRedisTemplate");
//    }
//
//    //    public RealDataTransPort() {
////        this.template = ApplicationContextProvider.getBean(StringRedisTemplate.class);
////    }
//    // 当前会话连接
//    private volatile Session session;
//
//    @OnOpen
//    public void onOpen(Session session) {
//        log.info("==========================有一用户发起连接，当前连接数为：" + PollThread.cachedSessions.size());
//    }
//
//    @OnClose
//    public void onClose() {
//        PollThread.removeCachedSessions(this.session);
//        log.error("==========================有一连接掉线，当前连接总数：" + +PollThread.cachedSessions.size());
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException {
//        log.info("=======================接收到客户端消息：" + message);
//        // jwt密签不是使用utf-8解签，需要自己指定
//        Claims claims = null;
//        try {
//            claims = Jwts.parser().setSigningKey(JwtKey.JWT_SIGNING_KEY.getBytes("UTF-8")).parseClaimsJws(message.replace("Bearer ", "")).getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.getBasicRemote().sendText("非法登录");
//        }
//        Integer enterpriseId = Integer.parseInt(claims.get("enterpriseId").toString());
//        if (enterpriseId == null) {
//            try {
//                session.getBasicRemote().sendText("验证失败，连接被强制关闭");
//                session.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        this.session = session;
//        PollThread.addCachedSessions(session, enterpriseId);
//        log.info("==========================有一用户发起登录，当前连接数为：" + PollThread.cachedSessions.size());
//        StringBuilder sb = new StringBuilder();
//        sb.delete(0, sb.length());
//        sb.append(enterpriseId).append("*");
//        Set<String> keys = template6.keys(sb.toString());
//        Iterator<String> iterator = keys.iterator();
//        while (iterator.hasNext()) {
//            String next = iterator.next();
//            Map<Object, Object> entries = template6.opsForHash().entries(next);
//            String equipNo = "";
//            VoMapEquipData voMapEquipData = new VoMapEquipData();
//            List<VoMapData> voMapDataList = new ArrayList<>();
////            遍历获取map值
//            for (Map.Entry<Object, Object> entry : entries.entrySet()) {
//                if (entry.getKey() != null && entry.getKey().toString().equals("equipNo")) {
//                    equipNo = entry.getValue().toString();
//                    sb.delete(0, sb.length());
//                    String h = sb.append(enterpriseId)
//                            .append(":")
//                            .append(equipNo)
//                            .append("*").toString();
////                    处理设备报警
//                    Set<String> equipAlarmKeys = template1.keys(h);
//                    if (equipAlarmKeys == null || equipAlarmKeys.size() == 0) {
//                        voMapEquipData.setType(0);
//                    } else {
//                        voMapEquipData.setType(1);
//                    }
//                    continue;
//                }
//                if (entry.getKey() != null && entry.getKey().toString().equals("enterpriseId")) {
//                    continue;
//                }
//                Object meterKey = entry.getKey();
//                Object value = entry.getValue();
//                if (value == null) {
//                    continue;
//                }
//                try {
//                    Map map = JSON.parseObject(value.toString(), Map.class);
//                    VoMapData voMapData = redisData(map, sb, enterpriseId, equipNo, meterKey);
//                    voMapDataList.add(voMapData);
//                } catch (Exception e) {
//                    List list = JSON.parseObject(value.toString(), List.class);
//                    for (Object obj : list) {
//                        Map map = JSON.parseObject(obj.toString(), Map.class);
//                        VoMapData voMapData = redisData(map, sb, enterpriseId, equipNo, meterKey);
//                        if (voMapData == null) {
//                            continue;
//                        }
//                        voMapDataList.add(voMapData);
//                    }
//                }
//            }
//            voMapEquipData.setCollectionTime(System.currentTimeMillis());
//            voMapEquipData.setEnterpriseId(enterpriseId);
//            voMapEquipData.setVoMapData(voMapDataList);
//            voMapEquipData.setEquipNo(equipNo);
//            synchronized (session) {
//                session.getBasicRemote().sendText(JSON.toJSONString(voMapEquipData));
//            }
//            log.info("#####################################################################发送成功##################################################");
//        }
//    }
//
//
//    private static VoMapData redisData(Map map, StringBuilder sb, Integer enterpriseId, String equipNo, Object meterKey) {
//
//        Object finalValue = map.get("finalValue") == null ? 0.00 : map.get("finalValue");
////                    设备参数id
//        Object equipParamNameId = map.get("equipParamNameId");
////                    仪表参数id
//        Object deviceParamNameId = map.get("deviceParamNameId");
//        Object unit = map.get("unit");
//        if (equipParamNameId == null || equipParamNameId.toString().equals("")) {
//            return null;
//        }
//        sb.delete(0, sb.length());
//        String h = sb.append(enterpriseId)
//                .append(":")
//                .append(equipNo)
//                .append(":")
//                .append(meterKey)
//                .append(":")
//                .append(deviceParamNameId).toString();
////        处理仪表报警
//        String s = template1.opsForValue().get(h);
//        VoMapData voMapData = new VoMapData();
////                    如果有报警 查询出来就不为空 返回值为1
//        if (s != null) {
//            voMapData.setAlarmType(1);
//        } else {
//            voMapData.setAlarmType(0);
//        }
////                    获取参数报警
//        voMapData.setValue(finalValue + "" + unit);
//        voMapData.setParamId(equipParamNameId.toString());
//        return voMapData;
//    }
//}
