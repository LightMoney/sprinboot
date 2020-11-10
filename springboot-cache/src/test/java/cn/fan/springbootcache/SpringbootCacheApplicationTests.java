package cn.fan.springbootcache;

import cn.fan.springbootcache.build.PowerCacheBuilder;
import cn.hutool.core.date.DateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
class SpringbootCacheApplicationTests {

    @Resource
    private PowerCacheBuilder cacheBuilder;
    @Test
    void contextLoads() {
    }
    @Test
    public void testCacheVerson() throws Exception {

        String version = cacheBuilder.getCacheVersion();
        System.out.println(String.format("当前缓存版本：%s", version));

        String cacheKey = cacheBuilder.generateVerKey("goods778899");

        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setGoodsId(UUID.randomUUID().toString());

        goodsVO.setGoodsType(1024);
        goodsVO.setGoodsCode("123456789");
        goodsVO.setGoodsName("我的测试商品");

        cacheBuilder.set(cacheKey, goodsVO);

        GoodsVO goodsVO1 = cacheBuilder.get(cacheKey);


        version = cacheBuilder.resetCacheVersion();
        System.out.println(String.format("重置后的缓存版本：%s", version));


        cacheKey = cacheBuilder.generateVerKey("goods112233");

        cacheBuilder.set(cacheKey, goodsVO);

        GoodsVO goodsVO2 = cacheBuilder.get(cacheKey);

        Assert.notNull(goodsVO2);

        Assert.isTrue(goodsVO1.getGoodsId().equals(goodsVO2.getGoodsId()),"两个缓存对象的主键相同");
    }
}
