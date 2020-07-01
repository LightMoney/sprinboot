//package cn.fan.config;
//
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//

///**
// * 多数据源 lettuce配置
// */
//@Configuration
//public class SpringbootConfig {
//
//    /**
//     * 配置lettuce连接池
//     *
//     * @return
//     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
//    public GenericObjectPoolConfig redisPool() {
//        return new GenericObjectPoolConfig();
//    }
//
//    /**
//     * 配置第一个数据源的
//     *
//     * @return
//     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.redis")
//    public RedisStandaloneConfiguration redisConfig() {
//        return new RedisStandaloneConfiguration();
//    }
//
//    /**
//     * 配置第二个数据源
//     *
//     * @return
//     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.redis2")
//    public RedisStandaloneConfiguration redisConfig2() {
//        return new RedisStandaloneConfiguration();
//    }
//
//    /**
//     * 配置第一个数据源的连接工厂
//     * 这里注意：需要添加@Primary 指定bean的名称，目的是为了创建两个不同名称的LettuceConnectionFactory
//     *
//     * @param config
//     * @param redisConfig
//     * @return
//     */
//    @Bean("factory")
//    @Primary
//    public LettuceConnectionFactory factory(GenericObjectPoolConfig config, RedisStandaloneConfiguration redisConfig) {
//        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
//        return new LettuceConnectionFactory(redisConfig, clientConfiguration);
//    }
//
//    @Bean("factory2")
//    public LettuceConnectionFactory factory2(GenericObjectPoolConfig config, RedisStandaloneConfiguration redisConfig2) {
//        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
//        return new LettuceConnectionFactory(redisConfig2, clientConfiguration);
//    }
//
//    /**
//     * 配置第一个数据源的RedisTemplate
//     * 注意：这里指定使用名称=factory 的 RedisConnectionFactory
//     * 并且标识第一个数据源是默认数据源 @Primary
//     *
//     * @param factory
//     * @return
//     */
//    @Bean("redisTemplate")
//    @Primary
//    public RedisTemplate<String, String> redisTemplate(@Qualifier("factory") RedisConnectionFactory factory) {
//        return getStringStringRedisTemplate(factory);
//    }
//
//    /**
//     * 配置第一个数据源的RedisTemplate
//     * 注意：这里指定使用名称=factory2 的 RedisConnectionFactory
//     *
//     * @param factory2
//     * @return
//     */
//    @Bean("redisTemplate2")
//    public RedisTemplate<String, String> redisTemplate2(@Qualifier("factory2") RedisConnectionFactory factory2) {
//        return getStringStringRedisTemplate(factory2);
//    }
//
//    /**
//     * 设置序列化方式 （这一步不是必须的）
//     *
//     * @param factory2
//     * @return
//     */
//    private RedisTemplate<String, String> getStringStringRedisTemplate(RedisConnectionFactory factory2) {
//        StringRedisTemplate template = new StringRedisTemplate(factory2);
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
//                Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setKeySerializer(template.getStringSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        //在使用String的数据结构的时候使用这个来更改序列化方式
//        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
//        template.setKeySerializer(stringSerializer);
//        template.setValueSerializer(stringSerializer);
//        template.setHashKeySerializer(stringSerializer);
//        template.setHashValueSerializer(stringSerializer);
//        return template;
//    }
//}
//
