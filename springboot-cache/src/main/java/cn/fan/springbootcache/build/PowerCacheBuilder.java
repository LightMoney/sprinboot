package cn.fan.springbootcache.build;

import cn.fan.springbootcache.config.AppConst;
import cn.fan.springbootcache.config.AppField;
import cn.fan.springbootcache.service.CacheProviderService;
import cn.fan.springbootcache.util.ConfigUtil;
import com.google.common.collect.Lists;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/*
 * 支持多缓存提供程序多级缓存的缓存帮助类
 * */
@Slf4j
@Configuration
@ComponentScan(basePackages = AppConst.BASE_PACKAGE_NAME)
public class PowerCacheBuilder {

    @Autowired
    @Qualifier("localCacheService")
    private CacheProviderService localCacheService;

    @Autowired
    @Qualifier("redisCacheService")
    private CacheProviderService redisCacheService;

    private static List<CacheProviderService> _listCacheProvider = Lists.newArrayList();

    private static final Lock providerLock = new ReentrantLock();

    /**
     * 初始化缓存提供者 默认优先级：先本地缓存，后分布式缓存
     **/
    private List<CacheProviderService> getCacheProviders() {

        if (_listCacheProvider.size() > 0) {
            return _listCacheProvider;
        }

        //线程安全
        try {
            providerLock.tryLock(1000, TimeUnit.MILLISECONDS);

            if (_listCacheProvider.size() > 0) {
                return _listCacheProvider;
            }

            String isUseCache = ConfigUtil.getConfigVal(AppField.IS_USE_LOCAL_CACHE);

            CacheProviderService cacheProviderService = null;

            //启用本地缓存
            if ("1".equalsIgnoreCase(isUseCache)) {
                _listCacheProvider.add(localCacheService);
            }

            isUseCache = ConfigUtil.getConfigVal(AppField.IS_USE_REDIS_CACHE);

            //启用Redis缓存
            if ("1".equalsIgnoreCase(isUseCache)) {
                _listCacheProvider.add(redisCacheService);

                resetCacheVersion();//设置分布式缓存版本号
            }

            log.info("初始化缓存提供者成功，共有" + _listCacheProvider.size() + "个");
        } catch (Exception e) {
            e.printStackTrace();

            _listCacheProvider = Lists.newArrayList();

            log.error("初始化缓存提供者发生异常：{}", e);
        } finally {
            providerLock.unlock();
        }

        return _listCacheProvider;
    }

    /**
     * 查询缓存
     *
     * @param key 缓存键 不可为空
     **/
    public <T extends Object> T get(String key) {
        T obj = null;

        //key = generateVerKey(key);//构造带版本的缓存键

        for (CacheProviderService provider : getCacheProviders()) {

            obj = provider.get(key);

            if (obj != null) {
                return obj;
            }
        }

        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key      缓存键 不可为空
     * @param function 如没有缓存，调用该callable函数返回对象 可为空
     **/
    public <T extends Object> T get(String key, Function<String, T> function) {
        T obj = null;

        for (CacheProviderService provider : getCacheProviders()) {

            if (obj == null) {
                obj = provider.get(key, function);
            } else if (function != null && obj != null) {//查询并设置其他缓存提供者程序缓存
                provider.get(key, function);
            }

            //如果callable函数为空 而缓存对象不为空 及时跳出循环并返回
            if (function == null && obj != null) {
                return obj;
            }

        }

        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key      缓存键 不可为空
     * @param function 如没有缓存，调用该callable函数返回对象 可为空
     * @param funcParm function函数的调用参数
     **/
    public <T extends Object, M extends Object> T get(String key, Function<M, T> function, M funcParm) {
        T obj = null;

        for (CacheProviderService provider : getCacheProviders()) {

            if (obj == null) {
                obj = provider.get(key, function, funcParm);
            } else if (function != null && obj != null) {//查询并设置其他缓存提供者程序缓存
                provider.get(key, function, funcParm);
            }

            //如果callable函数为空 而缓存对象不为空 及时跳出循环并返回
            if (function == null && obj != null) {
                return obj;
            }
        }

        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key        缓存键 不可为空
     * @param function   如没有缓存，调用该callable函数返回对象 可为空
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    public <T extends Object> T get(String key, Function<String, T> function, long expireTime) {
        T obj = null;

        for (CacheProviderService provider : getCacheProviders()) {

            if (obj == null) {
                obj = provider.get(key, function, expireTime);
            } else if (function != null && obj != null) {//查询并设置其他缓存提供者程序缓存
                provider.get(key, function, expireTime);
            }

            //如果callable函数为空 而缓存对象不为空 及时跳出循环并返回
            if (function == null && obj != null) {
                return obj;
            }
        }

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
    public <T extends Object, M extends Object> T get(String key, Function<M, T> function, M funcParm, long expireTime) {
        T obj = null;

        for (CacheProviderService provider : getCacheProviders()) {

            if (obj == null) {
                obj = provider.get(key, function, funcParm, expireTime);
            } else if (function != null && obj != null) {//查询并设置其他缓存提供者程序缓存
                provider.get(key, function, funcParm, expireTime);
            }

            //如果callable函数为空 而缓存对象不为空 及时跳出循环并返回
            if (function == null && obj != null) {
                return obj;
            }
        }

        return obj;
    }

    /**
     * 设置缓存键值  直接向缓存中插入或覆盖值
     *
     * @param key 缓存键 不可为空
     * @param obj 缓存值 不可为空
     **/
    public <T extends Object> void set(String key, T obj) {

        //key = generateVerKey(key);//构造带版本的缓存键

        for (CacheProviderService provider : getCacheProviders()) {

            provider.set(key, obj);

        }
    }

    /**
     * 设置缓存键值  直接向缓存中插入或覆盖值
     *
     * @param key        缓存键 不可为空
     * @param obj        缓存值 不可为空
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    public <T extends Object> void set(String key, T obj, Long expireTime) {

        //key = generateVerKey(key);//构造带版本的缓存键

        for (CacheProviderService provider : getCacheProviders()) {

            provider.set(key, obj, expireTime);

        }
    }

    /**
     * 移除缓存
     *
     * @param key 缓存键 不可为空
     **/
    public void remove(String key) {

        //key = generateVerKey(key);//构造带版本的缓存键

        if (StringUtils.isEmpty(key) == true) {
            return;
        }

        for (CacheProviderService provider : getCacheProviders()) {

            provider.remove(key);

        }
    }

    /**
     * 是否存在缓存
     *
     * @param key 缓存键 不可为空
     **/
    public boolean contains(String key) {
        boolean exists = false;

        //key = generateVerKey(key);//构造带版本的缓存键

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
     * 获取分布式缓存版本号
     **/
    public String getCacheVersion() {
        String version = "";
        boolean isUseCache = checkUseRedisCache();

        //未启用Redis缓存
        if (isUseCache == false) {
            return version;
        }

        version = redisCacheService.get(AppConst.CACHE_VERSION_KEY);

        return version;
    }

    /**
     * 重置分布式缓存版本  如果启用分布式缓存，设置缓存版本
     **/
    public String resetCacheVersion() {
        String version = "";
        boolean isUseCache = checkUseRedisCache();

        //未启用Redis缓存
        if (isUseCache == false) {
            return version;
        }

        //设置缓存版本
        version = String.valueOf(Math.abs(UUID.randomUUID().hashCode()));
        redisCacheService.set(AppConst.CACHE_VERSION_KEY, version);

        return version;
    }

    /**
     * 如果启用分布式缓存，获取缓存版本，重置查询的缓存key，可以实现相对实时的缓存过期控制
     * <p>
     * 如没有启用分布式缓存，缓存key不做修改，直接返回
     **/
    public String generateVerKey(String key) {

        String result = key;
        if (StringUtils.isEmpty(key) == true) {
            return result;
        }

        boolean isUseCache = checkUseRedisCache();

        //没有启用分布式缓存，缓存key不做修改，直接返回
        if (isUseCache == false) {
            return result;
        }

        String version = redisCacheService.get(AppConst.CACHE_VERSION_KEY);
        if (StringUtils.isEmpty(version) == true) {
            return result;
        }

        result = String.format("%s_%s", result, version);

        return result;
    }

    /**
     * 验证是否启用分布式缓存
     **/
    private boolean checkUseRedisCache() {
        boolean isUseCache = false;
        String strIsUseCache = ConfigUtil.getConfigVal(AppField.IS_USE_REDIS_CACHE);

        isUseCache = "1".equalsIgnoreCase(strIsUseCache);

        return isUseCache;
    }
}