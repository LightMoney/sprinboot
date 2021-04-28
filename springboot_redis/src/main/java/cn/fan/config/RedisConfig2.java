package cn.fan.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源 lettuce配置
 */
@Component
@Slf4j
public class RedisConfig2 {

    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String passWord;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdl;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdl;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.database}")
    private int db0;
    @Value("${spring.redis2.database}")
    private int db1;


    public static Map<Integer, StringRedisTemplate> redisTemplateMap = new HashMap<>();

    @PostConstruct
    public void initRedisTemp() throws Exception {
        log.info("###### START 初始化 Redis 连接池 START ######");
        redisTemplateMap.put(db0, redisTemplateObject(db0));
        redisTemplateMap.put(db1, redisTemplateObject(db1));

        log.info("###### END 初始化 Redis 连接池 END ######");
    }

    public StringRedisTemplate redisTemplateObject(Integer dbIndex) {
        StringRedisTemplate redisTemplateObject = new StringRedisTemplate(redisConnectionFactory(dbIndex));
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }

    /**
     * 连接池配置信息
     *
     * @return
     */
    public GenericObjectPoolConfig lettucePoolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        //最大空闲连接数
        poolConfig.setMaxIdle(maxIdl);
        //最小空闲连接数
        poolConfig.setMinIdle(minIdl);
        //最大连接数
        poolConfig.setMaxTotal(maxActive);
        //当池内没有可用的连接时，最大等待时间
        poolConfig.setMaxWaitMillis(maxWait);
        //------其他属性根据需要自行添加-------------
        return poolConfig;
    }

    /**
     * lettuce连接工厂
     *
     * @param db
     * @return
     */
    public RedisConnectionFactory redisConnectionFactory(int db) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(lettucePoolConfig()).build();
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(hostName);
        config.setPassword(RedisPassword.of(passWord));
        config.setPort(port);
        config.setDatabase(db);
        //设置连接工厂
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(config,clientConfiguration);
//        使用lettuce连接池这里必须加，否者会有null异常
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        //在使用String的数据结构的时候使用这个来更改序列化方式
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(stringSerializer);
    }

    /**
     * 根据db 获取对应的redisTemplate实例
     *
     * @param db
     * @return
     */
    public StringRedisTemplate getRedisTemplateByDb(int db) {
        return redisTemplateMap.get(db);
    }

}
