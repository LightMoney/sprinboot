//package cn.fan.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.lang.reflect.Method;
//import java.util.HashSet;
//import java.util.Set;
//lettuce 单数据源  其中@EnableCaching注解是为了使@Cacheable、@CacheEvict、@CachePut、@Caching注解生效
//@Configuration
//@EnableCaching
//public class RedisConfig {
//
//    @Autowired
//    private LettuceConnectionFactory lettuceConnectionFactory;
//    /**
//     * 自定义key值生成方式
//     * @return
//     */
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(target.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : params) {
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//    /**
//     * 默认情况下RedisTemplate模板只能支持字符串，我们自定义一个RedisTemplate，设置序列化器，这样我们可以很方便的操作实例对象。
//     * @param lettuceConnectionFactory
//     * @return
//     */
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
//        // 设置序列化
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
//                Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        // 配置redisTemplate
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(stringSerializer);// key序列化
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
//        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//
//    // 缓存管理器（这里可以设置默认的过期时间）
//    @Bean
//    public CacheManager cacheManager() {
//        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
//                .fromConnectionFactory(lettuceConnectionFactory);
//        //设置缓存空间集合
//        @SuppressWarnings("serial")
//        Set<String> cacheNames = new HashSet<String>() {
//            {
//                add("codeNameCache");
//            }
//        };
//
//        builder.initialCacheNames(cacheNames);
//        return builder.build();
//    }
//
//
//}
