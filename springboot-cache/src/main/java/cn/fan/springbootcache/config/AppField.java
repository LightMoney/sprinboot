package cn.fan.springbootcache.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class AppField {

    public static String IS_USE_LOCAL_CACHE = "";
    public static String IS_USE_REDIS_CACHE = "";

    @Value("${spring.power.isuselocalcache}")
    private String useLocalCache;
    @Value("${spring.power.isuserediscache}")
    private String useRedisCache;

    @PostConstruct
    public void setInit() {
        AppField.IS_USE_REDIS_CACHE = useRedisCache;
        AppField.IS_USE_LOCAL_CACHE = useLocalCache;
    }
}
