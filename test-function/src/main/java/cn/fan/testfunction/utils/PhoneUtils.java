package cn.fan.testfunction.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lgh
 * @date 2019/03/01 0001
 * @description 电话号码工具
 */
public class PhoneUtils {
    /**
     * 电话正则表达式
     */
    private static final String TELEPHOE_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

    private static final int TELEPHONE_FIX_LENGTH = 11;

    public static Boolean isPhone(String telephone) {
        // 手机号是否为11位
        if (telephone.length() != TELEPHONE_FIX_LENGTH) {
            return false;
        }
        // 是否匹配正则
        Pattern pattern = Pattern.compile(TELEPHOE_REGEX);
        Matcher matcher = pattern.matcher(telephone);
        return matcher.matches();
    }
}
