1.通过默认的template类获取对应连接池对象 然后切换数据库
Jedis：
 JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) redisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(index);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        
 lettuce:
 LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
         connectionFactory.setDatabase(2);
         redisTemplate.setConnectionFactory(connectionFactory);
         
         
 lua脚本中有中文会有异常