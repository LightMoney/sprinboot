package cn.fan.testfunction.utils;

import java.util.UUID;

/**
 * UUID生成工具
 */
public class UuidUtil {
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }


    /**
     *
     * (获取指定长度uuid)
     *
     * @return
     */
    public static  String getUUID(int len) {
        if(0 >= len) {
            return null;
        }
        String uuid = getUUID();
        System.out.println(uuid);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < len; i++) {
            str.append(uuid.charAt(i));
        }
        return str.toString();
    }

    public static  String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }



}
