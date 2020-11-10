 String cacheKey = cacheBuilder.generateVerKey("goods778899");
   cacheBuilder.set(cacheKey, goodsVO);
         GoodsVO goodsVO2 = cacheBuilder.get(cacheKey);
         
         
         PowerCacheBuilder为封装类 外部使用直接注入该类  get  set contain 
         
         
         1.缓存及时过期问题
         2.redis key和value都有限制，且都不能超过512M
         3.最大连接数和超时设置