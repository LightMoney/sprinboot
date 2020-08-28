package cn.fan.testfunction.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 
 * @data 2018年9月11日 下午4:14:54
 * @description 资源文件读取工具
 */
public class PropertiesFileUtils {

    // 当打开多个资源文件时，缓存资源文件
    private static HashMap<String, PropertiesFileUtils> configMap = new HashMap<String, PropertiesFileUtils>();
    // 打开文件时间，判断超时使用
    private Date loadTime = null;
    // 资源文件
    private ResourceBundle resourceBundle = null;
    // 默认资源文件名称
    private static final String NAME = "config";
    // 缓存时间
    private static final Integer TIME_OUT = 60 * 1000;

    // 私有构造方法，创建单例
    private PropertiesFileUtils(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PropertiesFileUtils getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PropertiesFileUtils getInstance(String name) {
        PropertiesFileUtils conf = configMap.get(name);
        if (null == conf) {
            conf = new PropertiesFileUtils(name);
            configMap.put(name, conf);
        }
        // 判断是否打开的资源文件是否超时1分钟
        if ((System.currentTimeMillis() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesFileUtils(name);
            configMap.put(name, conf);
        }
        return conf;
    }

    // 根据key读取value
    public String get(String key ,String... name) {
        try {
            String value = resourceBundle.getString(key);
            if (value==null || value.trim().equals(""))
                return name[0];
            return value;
        } catch (MissingResourceException e) {
            return "";
        }
    }

    // 根据key读取value(整形)
    public Integer getInt(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    // 根据key读取value(布尔)
    public boolean getBool(String key) {
        try {
            String value = resourceBundle.getString(key);
            if ("true".equals(value)) {
                return true;
            }
            return false;
        } catch (MissingResourceException e) {
            return false;
        }
    }

    public Date getLoadTime() {
        return loadTime;
    }

}
