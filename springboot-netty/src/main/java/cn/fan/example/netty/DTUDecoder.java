//package cn.fan.example.netty;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * code is far away from bug with the animal protecting
// * ┏┓　 ┏┓
// * ┏┛┻━━━┛┻┓
// * ┃　　　　　　┃
// * ┃　　　━　　┃
// * ┃　┳┛　┗┳　┃
// * ┃　　　　　　┃
// * ┃　　　┻　　┃
// * ┃　　　　　　┃
// * ┗━┓　　　┏━┛
// * 　　┃　　　┃神兽保佑
// * 　　┃　　　┃代码无BUG！
// * 　　┃　　　┗━━━┓
// * 　　┃　　　　　 ┣┓
// * 　　┃　　　　　 ┏┛
// * 　　┗┓┓ ┏━┳┓ ┏┛
// * 　　　┃┫┫　┃┫┫
// * 　　　┗┻┛　┗┻┛
// *
// * @Description : DTU 数据包解码器 继承 netty 的父解码器
// * 由于 dtu 的数据包的标志位（首尾字节）都是 0x55 ，
// * 所以先使用分隔符解码器 DelimiterBasedFrameDecoder 来将  0x55 先分开
// * 然后再将数据的首尾加上 0x55 来还原原始字节，
// * 该解码器拿到的数据是已经被 0x55 分割的数据，所以该类的功能是只将首尾字节加上 0x55 还原原始数据
// * ---------------------------------
// * @Author : zsq
// * @Date : 2019/1/18
// */
//public class DTUDecoder extends ByteToMessageDecoder {
//
//    private final Logger logger = LoggerFactory.getLogger(DTUDecoder.class);
//
//    /**
//     *
//     * @param channelHandlerContext
//     * @param inByte
//     * @param out
//     * @throws Exception
//     */
//    @Override
//    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf inByte, List<Object> out) throws Exception {
//        byte[] conn = new byte[inByte.readableBytes()];
//        inByte.readBytes(conn);
//        if (conn.length == 1) {
//            logger.error("not this value");
//            return;
//        }
//        //在知道list长度时指定长度可以不进行扩容，扩容非常影响性能
//        List<Byte> list = new ArrayList<>(conn.length+2);
//        // 首字节设为0x55
//        list.add((byte) 0x55);
//        // 将数组的字节复制到list
//        for(byte b:conn){
//            list.add(b);
//        }
//        // 尾字节设为0x55
//        list.add((byte) 0x55);
//        // 输出 进行转码后的 list
//        out.add(DTUDataConverter.convertDecode(list));
//    }
//}
