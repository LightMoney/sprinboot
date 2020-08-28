package cn.fan.testfunction.utils;

/**
 * @author lgh
 * @date 2019/01/04 0004
 * @description 字符串唯一编码工具
 */
public class OnlyIdUtils {

    /**
     * 前缀位数
     */
    private static final String DEFAULT_PREFIX = "";

    private static IdWorker idWorker = new IdWorker();

    /**
     * 调用雪花算法，加自定义前缀生成字符串编码
     *
     * @param prefix
     * @return
     */
    public static String generate(String prefix) {
        if (StringUtils.hasNotText(prefix)) {
            prefix = DEFAULT_PREFIX;
        }
        String nextId = idWorker.nextId();
        return prefix + nextId;
    }

}
