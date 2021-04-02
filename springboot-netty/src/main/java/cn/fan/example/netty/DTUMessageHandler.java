//package cn.fan.example.netty;
//
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.primitives.Bytes;
//import com.hthl.iot.gateway.tcp.dtu.cache.DtuConnectionCache;
//import com.hthl.iot.gateway.tcp.dtu.config.ConstantConfig;
//import com.hthl.iot.gateway.tcp.dtu.constant.enums.*;
//import com.hthl.iot.gateway.tcp.dtu.service.DTUProcessorService;
//import com.hthl.iot.gateway.tcp.dtu.utils.CRCTool;
//import com.hthl.iot.gateway.tcp.dtu.utils.TimeFormaterTool;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.util.ReferenceCountUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * DTU 协议接收到的字节的业务处理
// * 协议上写的有错，倒数二三位是先crc高位，后crc低位，第一次发起注册的数据区是登录的密码，登录时token不验证是指设备不验证，但是服务端还是要发送
// *
// * @author zhangshuaiqiang
// * @date 2018年3月29日
// */
//
//@ChannelHandler.Sharable
//@Component
//public class DTUMessageHandler extends ChannelInboundHandlerAdapter {
//
//
//    private static final Logger logger = LoggerFactory.getLogger(DTUDecoder.class);
//
//    // k:channalId  v:ctx
//    private static ConcurrentHashMap<String, ChannelHandlerContext> channel = new ConcurrentHashMap<>(64);
//    // k:channalId  v:设备id
//    private static ConcurrentHashMap<String, String> connectionHolder = new ConcurrentHashMap<>(64);
//    // k: 设备id  v： 设备的 token
//    public static ConcurrentHashMap<String, byte[]> deviceTokens = new ConcurrentHashMap<>(64);
//
//    // 注入 spring 容器的业务处理实例
//    @Autowired
//    private DTUProcessorService dtuProcessorService;
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//    @Autowired
//    private ConstantConfig constantConfig;
//
//
//    // 有连接的时候调用
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//    }
//
//    // 断开连接时调用
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        // 删除channel相对应的id和值
//        String chId = ctx.channel().id().toString();
//        channel.remove(chId);
//        // 删除通道对应的 仪表id
//        String deviceId = connectionHolder.remove(chId);
//        // 如果仪器id 不是 null，则删除web 端缓存，
//        // 因为存在连接后就断开，中途没有发任何消息而导致deviceId 为 null 的情况，故需要做 NPE 检查
//        if (deviceId != null) {
//            DtuConnectionCache.removeConn(deviceId);
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("time",System.currentTimeMillis());
//            jsonObject.put("terminalCode",deviceId);
//            kafkaTemplate.send(constantConfig.KAFKA_TOPIC_OFFLINE_WARN,jsonObject.toJSONString());
//            logger.info("====================仪表掉线了，编号为：" + deviceId + "========================");
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        List<Byte> bytes = (List<Byte>) msg;
//        List<Byte> after = DTUDataConverter.convertDecode(bytes);
//
//        if (!CRCTool.validCrc(after)) {
//            // 若crc 校验失败
//            if (deviceHasAdmin(ctx)) {
//                // 若已登陆
//                logger.error("================CRC校验失败,数据将被从缓存中删除=============");
//                ReferenceCountUtil.release(msg);
//            } else {
//                // 若未登陆
//                logger.error("================仪器未登陆,非法连接将被释放=============");
//                ctx.close();
//            }
//        }
//
//        logger.info("===================CRC16 校验通过==================");
//        byte orderType = after.get(3);//获取命令类别
//        byte orderVal = after.get(4);//获取命令字
//        DTUPacketEnum type = determineOrderType(orderType);
//        switch (type) { //判断命令类别
//            case ROAD:  // 登录和心跳
//                if (orderVal == RoadEnum.ADMIN.vals()) {// 登录操作
//                    processAdmin(after, ctx);//处理登录操作
//                } else if (orderVal == RoadEnum.HEARTBEAT.vals()) {//心跳检测
//                    processHeartBeat(after, ctx);//处理心跳操作
//                } else {// 非法操作释放掉该连接
//                    logger.error("登录操作的第三个指令.....非法,此次数据不做处理");
//                    ReferenceCountUtil.release(msg);
//                }
//                break;
//            case IMAGE: //图片
//                break;
//            case DATATR: //数据传输，历史数据，
//                processDataTransPort(after, ctx);
//                break;
//            case TERMINAL: //终端
//                if (orderVal == TerminalOprateEnum.REQUEST_TIME.vals()) {
//                    processRequestTime(after, ctx);//处理请求当前时间
//                } else if (orderVal == TerminalOprateEnum.DOWNLOAD.vals() && after.get(9) == OrderStatusEnum.SUCCESS.vals()) {
//                    logger.info("下载指令成功");
//                } else if (orderVal == TerminalOprateEnum.READ.vals()) {
//                    processReadAD(after, ctx);//处理读取的设备当前AD数据
//                } else if (orderVal == TerminalOprateEnum.WRITE.vals()) {
//                    logger.info("设置ad指令成功");
//                }
//                break;
//            default:
//                processDefaultOrder(ctx, msg);
//                break;
//        }
//
//    }
//
//    /**
//     * 处理读取的AD值，可以不用返回，这里是需要主动发送
//     */
//    private void processReadAD(List<Byte> after, ChannelHandlerContext ctx) {
////        解析配置服务器ip以及端口地址
//        if (after.get(26) == Params.DNS[0]) {
//            this.analysisIPAndProt(after);
//        }
//        //解析版本信息
//        if (after.get(26) == Params.VERSION[0]) {
//            this.analysisVersion(after);
//            logger.error(after.toString());
//        }
////        DTUResponse response = new DTUResponse(DTUPacketEnum.TERMINAL.vals(),
////                TerminalOprateEnum.WRITE.vals(), after.get(5), after.get(6), // 命令序号低位，命令序号高位
////                getOtherInfo(after), // 扩展信息
////                OrderStatusEnum.OFFER.vals(), // 命令执行状态为成功
////                getDeviceId(after), // 设备id
////                getDeviceToken(after), // token
////                new byte[]{after.get(26), after.get(27), after.get(28), after.get(29), after.get(30),
////                        after.get(31), after.get(32), after.get(33), after.get(34), after.get(35), after.get(36),
////                        after.get(37), after.get(38), after.get(39), after.get(40), after.get(41), after.get(42),
////                });
////        // 获取编码后的数据
////        byte[] resAfterEncoder = response.getResp();
//        // 将响应数据返回终端
////        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(resAfterEncoder));
//    }
//
//    /**
//     * 处理登录
//     */
//    private void processAdmin(List<Byte> after, ChannelHandlerContext ctx) {
//        //获取设备id的字节
//        byte[] deviceIDByte = getDeviceId(after);
//        // 解析id
//        String deviceId = getDeviceIdLong(deviceIDByte).toString();
//        logger.info("设备id：========================" + deviceId + "==================发起登录");
//        // 将连接的设备 id 缓存起来
//        logger.error("当前设备有：==========================" + connectionHolder);
//        if (!connectionHolder.containsValue(deviceId)) {
//            String channalId = ctx.channel().id().toString();
//            // 缓存仪器机器码
//            connectionHolder.put(channalId, deviceId);
//            // 缓存通道
//            channel.put(channalId, ctx);
//            // 缓存 仪表 token
//            deviceTokens.put(deviceId, getDeviceToken(after));
//            // 缓存到 web 端的缓存中，便于回调使用
//            DtuConnectionCache.addConn(deviceId, channalId);
//            List<Integer> data = new ArrayList<>();
//            data.add((int) Params.DNS[0]);
//            data.add((int) Params.DNS[1]);
//            data.add((int) Params.DNS[2]);
//
//            //TODO 删除的
//            //orderService.doSendOrder(deviceId, data, 240, 170);
//        }
//        // 给 190019 下发 指令
//        if (deviceId.equals("19")) {
//            // 封装响应数据对象
//            DTUResponse response = new DTUResponse(
//                    DTUPacketEnum.TERMINAL.vals(),
//                    TerminalOprateEnum.DOWNLOAD.vals(),
//                    after.get(5),
//                    after.get(6),
//                    getOtherInfo(after),
//                    OrderStatusEnum.OFFER.vals(),
//                    getDeviceId(after),
//                    getDeviceToken(after),
//                    getBin());
//            byte[] resAfterEncoder = response.getResp();
//            // 将响应数据返回终端
//            ctx.channel().writeAndFlush(Unpooled.copiedBuffer(resAfterEncoder));
//            logger.info("设备id：========================" + deviceId + "==================下发hery指令成功");
//
//        }
//
//        // 返回数据
//        DTUResponse response = new DTUResponse(
//                DTUPacketEnum.ROAD.vals(),
//                RoadEnum.ADMIN.vals(),
//                after.get(5),
//                after.get(6), // 命令序号低位，命令序号高位
//                getOtherInfo(after), // 扩展信息
//                OrderStatusEnum.SUCCESS.vals(), // 命令执行状态为成功
//                getDeviceId(after), // 设备id
//                getDeviceToken(after), // token
//                new byte[]{0x00, 0x00});// 数据区，登录操作返回的数据区可以随意写
//        // 获得响应终端的数据（对各种操作通用，除了心跳）
//        byte[] resAfterEncoder = response.getResp();
//        // 将响应数据返回终端
//        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(resAfterEncoder));
//        logger.info("设备id：========================" + deviceId + "==================登录成功");
//    }
//
//    /**
//     * 处理心跳
//     */
//    private void processHeartBeat(List<Byte> after, ChannelHandlerContext ctx) {
//        // 先判断是否登陆
//        if (!deviceHasAdmin(ctx)) {
//            logger.error("==========================仪表未登陆,连接将被断开=================");
//            ctx.close();
//            return;
//        }
//        // 若已登陆则处理后续
//        byte[] data = TimeFormaterTool.getCurrentDateByte();
//        DTUResponse response = new DTUResponse(
//                DTUPacketEnum.ROAD.vals(),
//                RoadEnum.HEARTBEAT.vals(),
//                after.get(5),
//                after.get(6), // 命令序号低位，命令序号高位
//                getOtherInfo(after), // 扩展信息
//                OrderStatusEnum.SUCCESS.vals(), // 命令执行状态为成功
//                getDeviceId(after), // 设备id
//                getDeviceToken(after), // token
//                data);// 数据区，心跳返回的数据区可写可不写，因为不需要返回数据
//        // 获得心跳返回数据（已编码）
//        byte[] resAfterEncoder = response.getHeartBeatResp();
//        // 将响应数据返回终端
//        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(resAfterEncoder));
//    }
//
//    /**
//     * 确定命令类别
//     */
//    private DTUPacketEnum determineOrderType(byte type) {
//        DTUPacketEnum types = null;
//        if (type == DTUPacketEnum.ROAD.vals()) {
//            types = DTUPacketEnum.ROAD;
//        } else if (type == DTUPacketEnum.DATATR.vals()) {
//            types = DTUPacketEnum.DATATR;
//        } else if (type == DTUPacketEnum.IMAGE.vals()) {
//            types = DTUPacketEnum.IMAGE;
//        } else if (type == DTUPacketEnum.TERMINAL.vals()) {
//            types = DTUPacketEnum.TERMINAL;
//        }
//        return types;
//    }
//
//    /**
//     * 处理请求服务器时间
//     */
//    private void processRequestTime(List<Byte> after, ChannelHandlerContext ctx) {
//        // 先判断是否登陆
//        if (!deviceHasAdmin(ctx)) {
//            logger.error("==========================仪表未登陆,连接将被断开=================");
//            ctx.close();
//            return;
//        }
//        // 若已登陆则处理后续
//        byte[] data = TimeFormaterTool.getCurrentDateByte();
//        DTUResponse response = new DTUResponse(DTUPacketEnum.TERMINAL.vals(),
//                TerminalOprateEnum.SEND_TIME.vals(), after.get(5), after.get(6), // 命令序号低位，命令序号高位
//                getOtherInfo(after), // 扩展信息
//                OrderStatusEnum.OFFER.vals(), // 命令执行状态为主动发送
//                getDeviceId(after), // 设备id
//                getDeviceToken(after), // token
//                data);// 数据区（时间的字节表示），心跳请求不用返回数据
//        // 获得返回数据（已编码）
//        byte[] resAfterEncoder = response.getResp();
//        // 将响应数据返回终端
//        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(resAfterEncoder));
//    }
//
//
//    /**
//     * 处理默认的消息事件
//     *
//     * @param ctx
//     */
//    private void processDefaultOrder(ChannelHandlerContext ctx, Object msg) {
//        if (deviceHasAdmin(ctx)) {
//            logger.error("==========================仪表未登陆且发送非法指令，连接将被断开=================");
//            ctx.close();
//        } else {
//            logger.error("==========================仪表" + connectionHolder.get(ctx.channel().id().toString()) + "发送非法指令，不予响应=================");
//            ReferenceCountUtil.release(msg);
//        }
//    }
//
//
//    /**
//     * 判断仪表是否已经登陆，除登陆操作外每个指令都必须做校验
//     *
//     * @param ctx
//     * @return
//     */
//    private boolean deviceHasAdmin(ChannelHandlerContext ctx) {
//        String channalId = ctx.channel().id().toString();
//        // 若未登陆或已掉线则 deviceId 为 null
//        String deviceId = connectionHolder.get(channalId);
//        if (deviceId == null) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 处理数据传输的消息
//     *
//     * @param after
//     * @param ctx
//     */
//    private void processDataTransPort(List<Byte> after, ChannelHandlerContext ctx) {
//        // 先判断是否登陆
//        if (!deviceHasAdmin(ctx)) {
//            logger.error("==========================仪表未登陆,连接将被断开=================");
//            ctx.close();
//            return;
//        }
//        //发送原始数据到业务层
//        dtuProcessorService.decodeOriginByteDataToMap(connectionHolder.get(ctx.channel().id().toString()), after);
//        // 返回响应的数据区，原数据区直接返回
//        //data--->N字节  从bytes中获取
//        byte[] data = new byte[after.size() - 29];
//        int b = 0;
//        for (int i = 26; i < after.size() - 3; i++) {
//            data[b] = after.get(i);
//            b++;
//        }
//        // 封装响应数据对象
//        DTUResponse response = new DTUResponse(
//                after.get(3),
//                after.get(4),
//                after.get(5),
//                after.get(6),
//                getOtherInfo(after),
//                (byte) 0,
//                getDeviceId(after),
//                getDeviceToken(after),
//                data);
//        // 将响应数据返回终端
//        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(response.getResp()));
//    }
//
//
//    /**
//     * 读取文件内容
//     *
//     * @return
//     */
//    private byte[] getBin() {
//        InputStream input = null;
//        String filePath = "";
//        //FIXME 修改文件路径
//        //"netty\\0_ST1801模板15：2天信13流量计1压变20191226_142926.bin"
//        try {
//            input = new FileInputStream(new File(filePath));
//            List<Byte> bin = new ArrayList<Byte>();
//            int len;
//            while ((len = input.read()) != -1) {
//                bin.add((byte) len);
//            }
//            return Bytes.toArray(bin);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                input.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                logger.error("input关闭失败");
//            }
//        }
//        return null;
//    }
//
//
//    // 有设备已经注册到EventLoop时调用
//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        super.channelRegistered(ctx);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
//    }
//
//
//    /**
//     * 解析连接仪表的 ID 数据
//     *
//     * @param deviceID
//     * @return
//     */
//    public static Long getDeviceIdLong(byte[] deviceID) {
//        long sensor = 0;
//        for (int i = 0; i < deviceID.length; i++) {
//            int shift = i * 8;
//            sensor += (long) (deviceID[i] & 0xFF) << shift;
//        }
//        return sensor;
//    }
//
//    // 获取扩展信息 字节区
//    private static byte[] getOtherInfo(List<Byte> bytes) {
//        byte[] res = {bytes.get(7), bytes.get(8)};
//        return res;
//    }
//
//
//    // 获取设备 id 字节区
//    private static byte[] getDeviceId(List<Byte> bytes) {
//        byte[] res = {bytes.get(10), bytes.get(11), bytes.get(12), bytes.get(13), bytes.get(14),
//                bytes.get(15), bytes.get(16), bytes.get(17)};
//        return res;
//    }
//
//    // 获取设备 token 字节区
//    private static byte[] getDeviceToken(List<Byte> bytes) {
//        byte[] res = {bytes.get(18), bytes.get(19), bytes.get(20), bytes.get(21), bytes.get(22),
//                bytes.get(23), bytes.get(24), bytes.get(25)};
//        return res;
//    }
//
//    /**
//     * 发送指令
//     *
//     * @param deviceId 发送的仪表id
//     * @param orders   发送的仪表消息体
//     * @return
//     */
//    public static boolean sendOrderToDevice(String deviceId, byte[] orders) {
//        // web 缓存获取 通道号
//        String channalId = DtuConnectionCache.getConn(deviceId);
//        // 通过通道号获取 上下文
//        ChannelHandlerContext ctx = channel.get(channalId);
//        if (ctx != null) {
//            try {
//                // 发送指令
//                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(orders));
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 把仪表id 重新转换为 8字节数组
//     *
//     * @param deviceId
//     * @return
//     */
//    public static byte[] getDeviceIdByteArray(String deviceId) {
//        Long longId = Long.parseLong(deviceId);
//        byte b0 = (byte) ((longId & 0x00000000000000ffL));
//        byte b1 = (byte) ((longId & 0x000000000000ff00L) >> 8);
//        byte b2 = (byte) ((longId & 0x0000000000ff0000L) >> 16);
//        byte b3 = (byte) ((longId & 0x00000000ff000000L) >> 24);
//        byte b4 = (byte) ((longId & 0x000000ff00000000L) >> 32);
//        byte b5 = (byte) ((longId & 0x0000ff0000000000L) >> 40);
//        byte b6 = (byte) ((longId & 0x00ff000000000000L) >> 48);
//        byte b7 = (byte) ((longId & 0xff00000000000000L) >> 56);
//        return new byte[]{b0, b1, b2, b3, b4, b5, b6, b7};
//    }
//
//    /**
//     * 解析端口和ip
//     *
//     * @param after：返回的端口和ip数据
//     */
//    private void analysisIPAndProt(List<Byte> after) {
////        端口数据区
//        byte[] prots = {after.get(29), after.get(30)};
//        String s1 = Integer.toBinaryString(prots[0] & 0xff);
//        String s2 = Integer.toBinaryString(prots[1]);
//        String str = s2 + s1;
//        String prot = Integer.valueOf(str, 2).toString();
//
//        //获取设备id的字节
//        byte[] deviceIDByte = getDeviceId(after);
//        // 解析id
//        String code = getDeviceIdLong(deviceIDByte).toString();
////        ip
//        String ip = "";
////        ip区域  从32开始到包尾往前推27个字节总共59个字节
//        byte[] ips = new byte[59];
////        获取ip区域
//        for (int i = 33; i <= after.size(); i++) {
//            if (after.get(i) == 0) {
//                logger.error("跳出ip数据区域=========================》：区域值为：" + ips);
//                break;
//            }
//            // 如果30代表是 16进制的30话，就取16
////            // 如果30代表是 10进制的30话，就取10
//            char result = (char) Integer.parseInt(after.get(i) + "", 10);
//            ip = ip + (result + "");
//        }
//        //TODO 删除了下边
////        deviceConfigInfoService.deleteUserByCode(code);
////        int i = deviceConfigInfoService.addDeviceConfigInfo(ip, prot, code);
////        if (i == 1) {
////            logger.error("更新网关配置信息成功");
////        } else {
////            logger.error("更新网关配置信息成功");
////        }
//        logger.error("==========================>ip:" + ip + "   prot:" + prot + "    code:" + code);
//
//    }
//
//    /**
//     * 解析版本信息
//     *
//     * @param after：原始字节
//     */
//    private void analysisVersion(List<Byte> after) {
//        //获取设备id的字节
//        byte[] deviceIDByte = getDeviceId(after);
//        // 解析id
//        String code = getDeviceIdLong(deviceIDByte).toString();
//        String version = "";
//        for (int i = 28; i < after.size() - 3; i++) {
//            version = version + after.get(i) + ",";
//        }
//        version = version.substring(0, version.length() - 1);
//        //TODO 注释了下边
////        int i = deviceConfigInfoService.updateDeviceConfigVersion(code, version);
////        if (i != 1) {
////            logger.error("添加版本信息失败");
////        } else {
////            logger.error("添加版本信息成功");
////        }
//    }
//}
