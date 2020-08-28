package cn.fan.testfunction.utils;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * 利用cglib 技术进行bean 之间的负责，是BeanUtils 的 性能优化版本
 */
public class BeanCopierUtils {

    private static Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    public static void copyProperties(Object source, Object target) {
        String key = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (beanCopierMap.containsKey(key)) {
            copier = beanCopierMap.get(key);
        } else {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(key,copier);
        }
        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        String key = class1.toString() + class2.toString();
        return key;
    }
}
