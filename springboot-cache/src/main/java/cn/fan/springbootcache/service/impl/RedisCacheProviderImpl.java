package cn.fan.springbootcache.service.impl;


import cn.fan.springbootcache.config.AppConst;
import cn.fan.springbootcache.service.CacheProviderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Configuration
@ComponentScan(basePackages = AppConst.BASE_PACKAGE_NAME)
@Qualifier("redisCacheService")
public class RedisCacheProviderImpl implements CacheProviderService {

    @Resource
    private RedisTemplate<Serializable, Object> redisTemplate;


    /**
     * 查询缓存
     *
     * @param key 缓存键 不可为空
     **/
    @Override
    public <T extends Object> T get(String key) {
        T obj = get(key, null, null, AppConst.CACHE_MINUTE);

        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key      缓存键 不可为空
     * @param function 如没有缓存，调用该callable函数返回对象 可为空
     **/
    @Override
    public <T extends Object> T get(String key, Function<String, T> function) {
        T obj = get(key, function, key, AppConst.CACHE_MINUTE);

        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key      缓存键 不可为空
     * @param function 如没有缓存，调用该callable函数返回对象 可为空
     * @param funcParm function函数的调用参数
     **/
    @Override
    public <T extends Object, M extends Object> T get(String key, Function<M, T> function, M funcParm) {
        T obj = get(key, function, funcParm, AppConst.CACHE_MINUTE);

        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key        缓存键 不可为空
     * @param function   如没有缓存，调用该callable函数返回对象 可为空
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    @Override
    public <T extends Object> T get(String key, Function<String, T> function, Long expireTime) {
        T obj = get(key, function, key, expireTime);

        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key        缓存键 不可为空
     * @param function   如没有缓存，调用该callable函数返回对象 可为空
     * @param funcParm   function函数的调用参数
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    @Override
    public <T extends Object, M extends Object> T get(String key, Function<M, T> function, M funcParm, Long expireTime) {
        T obj = null;
        if (StringUtils.isEmpty(key) == true) {
            return obj;
        }

        expireTime = getExpireTime(expireTime);

        try {

            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            obj = (T) operations.get(key);
            if (function != null && obj == null) {
                obj = function.apply(funcParm);
                if (obj != null) {
                    set(key, obj, expireTime);//设置缓存信息
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * 设置缓存键值  直接向缓存中插入值，这会直接覆盖掉给定键之前映射的值
     *
     * @param key 缓存键 不可为空
     * @param obj 缓存值 不可为空
     **/
    @Override
    public <T extends Object> void set(String key, T obj) {

        set(key, obj, AppConst.CACHE_MINUTE);
    }

    /**
     * 设置缓存键值  直接向缓存中插入值，这会直接覆盖掉给定键之前映射的值
     *
     * @param key        缓存键 不可为空
     * @param obj        缓存值 不可为空
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    @Override
    public <T extends Object> void set(String key, T obj, Long expireTime) {
        if (StringUtils.isEmpty(key) == true) {
            return;
        }

        if (obj == null) {
            return;
        }

        expireTime = getExpireTime(expireTime);

        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();

        operations.set(key, obj);

        redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 移除缓存
     *
     * @param key 缓存键 不可为空
     **/
    @Override
    public void remove(String key) {
        if (StringUtils.isEmpty(key) == true) {
            return;
        }

        redisTemplate.delete(key);
    }

    /**
     * 是否存在缓存
     *
     * @param key 缓存键 不可为空
     **/
    @Override
    public boolean contains(String key) {
        boolean exists = false;
        if (StringUtils.isEmpty(key) == true) {
            return exists;
        }

        Object obj = get(key);

        if (obj != null) {
            exists = true;
        }

        return exists;
    }

    /**
     * 获取过期时间 单位：毫秒
     *
     * @param expireTime 传人的过期时间 单位毫秒 如小于1分钟，默认为10分钟
     **/
    private Long getExpireTime(Long expireTime) {
        Long result = expireTime;
        if (expireTime == null || expireTime < AppConst.CACHE_MINUTE / 10) {
            result = AppConst.CACHE_MINUTE;
        }

        return result;
    }
}