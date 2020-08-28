package cn.fan.testfunction.utils;

import java.util.regex.Pattern;

/**
 * @author lgh
 * @date 2019/01/02 0002
 * @description 字符串处理工具
 */
public class StringUtils {

    /**
     * 空标志
     */
    public static final String EMPTY = "";

    /**
     * 判断文本中是否有内容
     * <pre>
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str
     * @return
     */
    public static boolean hasText(String str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否没有文本
     *
     * @param str
     * @return
     */
    public static boolean hasNotText(String str) {
        return !StringUtils.hasText(str);
    }


    /**
     * 判断数组中是否都有内容
     *
     * @param strArr
     * @return
     */
    public static boolean hasText(String... strArr) {
        for (String str : strArr) {
            if (!StringUtils.hasText(str)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断对象是否为空
     * <pre>
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }


    /**
     * 判断对象是否不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(Object str) {
        return !StringUtils.isEmpty(str);
    }

    /**
     * 判断数组是否为空
     *
     * @param strArr
     * @return
     */
    public static boolean isEmpty(Object... strArr) {
        for (Object str : strArr) {
            if (StringUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为空白
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否不为空白
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * 判断数组是否有元素为空
     *
     * @param strArr
     * @return
     */
    public static boolean isBlank(String... strArr) {
        for (String str : strArr) {
            if (StringUtils.isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清除字符串前后的空格
     * <pre>
     * StringUtils.clean(null)          = ""
     * StringUtils.clean("")            = ""
     * StringUtils.clean("abc")         = "abc"
     * StringUtils.clean("    abc    ") = "abc"
     * StringUtils.clean("     ")       = ""
     * </pre>
     *
     * @param str
     * @return
     */
    public static String clean(String str) {
        return str == null ? EMPTY : str.trim();
    }


    /**
     * 比较两个字符串是否相等
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * 比较两个字符串是否相等，忽略大小写
     * <pre>
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * 比较两个字符串是否相等，忽略前后空格
     * <pre>
     * StringUtils.equalsIgnoreBlank(null, null)   = true
     * StringUtils.equalsIgnoreBlank(null, "abc")  = false
     * StringUtils.equalsIgnoreBlank("abc", null)  = false
     * StringUtils.equalsIgnoreBlank("abc", "abc") = true
     * StringUtils.equalsIgnoreBlank("abc", "abc  ") = true
     * </pre>
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsIgnoreBlank(String str1, String str2) {
        return str1 == null ? str2 == null : str2 == null ? false : str1.trim().equals(str2.trim());
    }

    /**
     * 忽略大小写比较后缀时候相等
     * <pre>
     * StringUtils.endsWithIgnoreCase("abc.jpg", "jpg")   = true
     * StringUtils.endsWithIgnoreCase(null, "jpg")  = false
     * StringUtils.endsWithIgnoreCase("abc.jpg", null)  = false
     * StringUtils.endsWithIgnoreCase("abc.jpg", "JPG") = true
     * </pre>
     *
     * @param str
     * @param suffix
     * @return
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return (str != null && suffix != null && str.length() >= suffix.length() &&
                str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length()));
    }


    /**
     * 速度快
     * 判断是否为整数
     *
     * @param str 传入字符串
     * @return 是整数返回true，否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否是字母
     *
     * @param str 传入字符串
     * @return 是字母返回true，否则返回false
     */
    public static boolean isBigAlpha(String str) {
        if (str == null) return false;
        return str.matches("[A-Z]+");
    }

    /**
     * 判断是否是字母或者数字
     *
     * @param str 传入字符串
     * @return 是字母返回true，否则返回false
     */
    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);

    }

    //===================================== private method ~ ===========================================================

    private static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

}
