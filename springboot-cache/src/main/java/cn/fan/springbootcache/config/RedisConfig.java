package cn.fan.springbootcache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis缓存配置类
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        //Jedis的Key和Value的序列化器默认值是JdkSerializationRedisSerializer
        //经实验，JdkSerializationRedisSerializer通过RedisDesktopManager看到的键值对不能正常解析

        //设置key的序列化器
        template.setKeySerializer(new StringRedisSerializer());

        ////设置value的序列化器  默认值是JdkSerializationRedisSerializer
        //使用Jackson序列化器的问题是，复杂对象可能序列化失败，比如JodaTime的DateTime类型

        //        //使用Jackson2，将对象序列化为JSON
        //        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //        //json转对象类，不设置默认的会将json转成hashmap
        //        ObjectMapper om = new ObjectMapper();
        //        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //        jackson2JsonRedisSerializer.setObjectMapper(om);
        //        template.setValueSerializer(jackson2JsonRedisSerializer);

        //将redis连接工厂设置到模板类中
        template.setConnectionFactory(factory);

        return template;
    }

//    //自定义缓存key生成策略
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
//                StringBuffer sb = new StringBuffer();
//                sb.append(target.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : params) {
//                    if (obj == null) {
//                        continue;
//                    }
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
}