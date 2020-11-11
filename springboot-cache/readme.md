 该缓存系统分为本地缓存和分布式缓存
    本地缓存采用guava 
    分布式缓存采用 redis
 
 String cacheKey = cacheBuilder.generateVerKey("goods778899");
   cacheBuilder.set(cacheKey, goodsVO);
         GoodsVO goodsVO2 = cacheBuilder.get(cacheKey);
         
         
         PowerCacheBuilder为封装类 外部使用直接注入该类  get  set contain 
        1.热key问题：突然有几十万的请求取访问特定的key导致redis宕机
            将热key放到不同的服务器
            加入二级缓存，redis宕掉 ，走本地缓存 
        2.缓存击穿
        3.缓存穿透
         //查询缓存
                List<User> users = (List<User>) redisTemplate.opsForValue().get("allUsers");
                /**
                 * 在高并发的情况下可能会出现缓存击穿的问题，此刻采用双重检测锁，
                 * 假设有1万个人进行查询，此刻缓存为null，判断users==null，一万人进入，此时对象已上锁，那么
                 * 等待，第一个人成功查询了数据库，并放入缓存，退出，第二个人进入同步代码块，此刻再去缓存中进行查询
                 * 到值，退出，第三个。。。到一万个，那么等下一万个人就不比进入同步锁，直接在上方的缓存中就可以查询
                 * 到值。
                 */
                if (users == null){
                    synchronized (this){
                        users = (List<User>) redisTemplate.opsForValue().get("allUsers");
                        if (users == null){
                            //缓存为null,查询一遍数据库。
                            users = userMapper.selectAllStudent();
                            redisTemplate.opsForValue().set("allUsers",users);
                        }
                    }
        
                } 
                
                4.缓存雪崩
                    针对不同key设置不同的过期时间，避免同时过期
                    限流，如果redis宕机，可以限流，避免同时刻大量请求打崩DB
                    二级缓存，同热key的方案。
                5.双写不一致问题
         1.缓存及时过期问题
         2.redis 字符串类型key和value都有限制，且都不能超过512M
         3.最大连接数和超时设置