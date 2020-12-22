package cn.fan.shirotoken.utils;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2020/12/18 17:22
 */
public class TokenGenerator {
    public static String generateValue() {
        return generateValue(UUID.randomUUID().toString());
    }

    private static final char[] hexCode = "0123456789abcdefgh".toCharArray();

    public static String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    /**
     * 生成Token
     * @param param
     * @return
     */
    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new RuntimeException("生成Token失败");
        }
    }
}
