//package cn.fan.example.netty;
//
//import com.hthl.iot.gateway.tcp.dtu.constant.enums.DTUPacketEnum;
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
// * @Description :
// * ---------------------------------
// * @Author : zsq
// * @Date : 2019/1/18
// */
//public class DTUDataConverter {
//
//
//    /**
//     * 回复服务器之前进行编码
//     * 0x55 -->0x54 0x01
//     * 0x54 -->0x54 0x02
//     *
//     * @return 转换后的字节数组
//     */
//    public static byte[] convertEncode(byte[] list) {
//        List<Byte> afterConvert = new ArrayList<Byte>();
//        afterConvert.add(list[0]);
//        for (int i = 1; i < list.length - 1; i++) {
//            if (list[i] == DTUPacketEnum.FLAG.vals()) {
//                afterConvert.add((byte) DTUPacketEnum.FLAG.vals());
//                afterConvert.add((byte) DTUPacketEnum.V2.vals());
//            } else if (list[i] == DTUPacketEnum.HEAD.vals()) {
//                afterConvert.add((byte) DTUPacketEnum.FLAG.vals());
//                afterConvert.add((byte) DTUPacketEnum.V1.vals());
//            } else {
//                afterConvert.add(list[i]);
//            }
//        }
//        afterConvert.add(list[list.length - 1]);
//        byte[] result = new byte[afterConvert.size()];
//        for (int i = 0; i < afterConvert.size(); i++) {
//            result[i] = afterConvert.get(i);
//        }
//        return result;
//    }
//
//    /**
//     * 转换取出的特殊字节码
//     * 0x54 0x01 -->0x55
//     * 0x54 0x02 -->0x54
//     *
//     * @return 转换后的字节
//     */
//    public static List<Byte> convertDecode(List<Byte> list) {
//        List<Byte> afterConvert = new ArrayList<Byte>();
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i) != DTUPacketEnum.FLAG.vals()) {
//                afterConvert.add(list.get(i));
//            } else {
//                if (list.get(i + 1) == DTUPacketEnum.V1.vals()) {
//                    afterConvert.add((byte) 0x55);
//                    i++;
//                } else if (list.get(i + 1) == DTUPacketEnum.V2.vals()) {
//                    afterConvert.add((byte) 0x54);
//                    i++;
//                } else {
//                    afterConvert.add(list.get(i));
//                }
//            }
//        }
//        return afterConvert;
//    }
//}
