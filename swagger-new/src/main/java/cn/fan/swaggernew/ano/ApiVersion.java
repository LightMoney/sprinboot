package cn.fan.swaggernew.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiVersion {
 
    /**
     * 接口版本号(对应swagger中的group)
     * @return String[]
     */
    String[] group();
 
}
