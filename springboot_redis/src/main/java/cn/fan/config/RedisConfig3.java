//package cn.fan.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.JedisShardInfo;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//多数据源 jedis 配置
//@Component
//@Slf4j
//public class RedisConfig3 {
//
//    @Value("${redis.host}")
//    private String hostName;
//    @Value("${redis.port}")
//    private int port;
//    @Value("${redis.password}")
//    private String passWord;
//    @Value("${redis.maxIdle}")
//    private int maxIdl;
//    @Value("${redis.minIdle}")
//    private int minIdl;
//    @Value("${redis.timeout}")
//    private int timeout;
//
//    @Value("${db0}")
//    private int db0;
//    @Value("${db1}")
//    private int db1;
//    @Value("${db3}")
//    private int db3;
//    @Value("${db6}")
//    private int db6;
//
//    public static Map<Integer, StringRedisTemplate> redisTemplateMap = new HashMap<>();
//
//    @PostConstruct
//    public void initRedisTemp() throws Exception{
//        log.info("###### START 初始化 Redis 连接池 START ######");
//        redisTemplateMap.put(db0,redisTemplateObject(db0));
//        redisTemplateMap.put(db1,redisTemplateObject(db1));
//        redisTemplateMap.put(db3,redisTemplateObject(db3));
//        redisTemplateMap.put(db6,redisTemplateObject(db6));
//        log.info("###### END 初始化 Redis 连接池 END ######");
//    }
//
//    public StringRedisTemplate redisTemplateObject(Integer dbIndex) {
//        StringRedisTemplate redisTemplateObject = new StringRedisTemplate();
//        redisTemplateObject.setConnectionFactory(redisConnectionFactory(jedisPoolConfig(),dbIndex));
//        setSerializer(redisTemplateObject);
//        redisTemplateObject.afterPropertiesSet();
//        return redisTemplateObject;
//    }
//
//    /**
//     * 连接池配置信息
//     * @return
//     */
//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig poolConfig=new JedisPoolConfig();
//        //最大连接数
//        poolConfig.setMaxIdle(maxIdl);
//        //最小空闲连接数
//        poolConfig.setMinIdle(minIdl);
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnReturn(true);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setNumTestsPerEvictionRun(10);
//        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
//        //当池内没有可用的连接时，最大等待时间
//        poolConfig.setMaxWaitMillis(10000);
//        //------其他属性根据需要自行添加-------------
//        return poolConfig;
//    }
//    /**
//     * jedis连接工厂
//     * @param jedisPoolConfig
//     * @return
//     */
//    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig, int db) {
//        JedisShardInfo jedisShardInfo = new JedisShardInfo(hostName, port);
//        jedisShardInfo.setPassword(passWord);
//        jedisShardInfo.setConnectionTimeout(timeout);
//        //设置连接工厂
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisShardInfo);
//        connectionFactory.setDatabase(db);
//        connectionFactory.setPoolConfig(jedisPoolConfig);
//        return connectionFactory;
//    }
//
//    private void setSerializer(StringRedisTemplate template) {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
//                Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setKeySerializer(template.getStringSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashKeySerializer(template.getStringSerializer());
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        //在使用String的数据结构的时候使用这个来更改序列化方式
//        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
//        template.setKeySerializer(stringSerializer );
//        template.setValueSerializer(stringSerializer );
//        template.setHashKeySerializer(stringSerializer );
//        template.setHashValueSerializer(stringSerializer );
//    }
//
//    /**
//     * 根据db 获取对应的redisTemplate实例
//     * @param db
//     * @return
//     */
//    public StringRedisTemplate getRedisTemplateByDb(int db){
//        return redisTemplateMap.get(db);
//    }
//
//}
