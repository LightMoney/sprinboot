package cn.fan.testfunction.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * @author lgh
 * @date 2019/02/27 0027
 * @description bean操作工具
 */
public class BeanUtils {


    /**
     * bean复制
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, null, (String[]) null);
    }

    // ========================================  用于属性复制（修改部分判断） ~ ==========================================

    /**
     * 保证目标属性为null，源文件不为null时候复制
     *
     * @param source
     * @param target
     * @throws BeansException
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties)
            throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            // 判断目标是否为null,不为null不操作
            Method readTargetMethod = targetPd.getReadMethod();
            try {
                if (!Modifier.isPublic(readTargetMethod.getDeclaringClass().getModifiers())) {
                    readTargetMethod.setAccessible(true);
                }
                Object value = readTargetMethod.invoke(target);
                if (value != null) {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            // 为null不复制
                            if (value != null) {
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }
}
