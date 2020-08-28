package cn.fan.testfunction.utils;

import java.util.Random;

/**
 * @author lgh
 * @date 2019/03/01 0001
 * @description
 */
public class PasswordUtils {
    /**
     * 字母范围
     */
    private final static String[] word = {
            "a", "b", "c", "d", "e", "f", "g",
            "h", "j", "k", "m", "n",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G",
            "H", "J", "K", "M", "N",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 数字范围
     */
    private final static String[] num = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    public static String randomPassword(Integer length) {
        StringBuffer stringBuffer = new StringBuffer();
        // 设置种子值，使得相同概率更小
        Random random = new Random(System.currentTimeMillis());
        boolean flag = false;
        for (int i = 0; i < length; i++) {
            if (flag) {
                stringBuffer.append(num[random.nextInt(num.length)]);
            } else {
                stringBuffer.append(word[random.nextInt(word.length)]);
            }
            flag = !flag;
        }
        return stringBuffer.toString();
    }
}
