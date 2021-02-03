package cn.fan.web;


import cn.fan.config.RedisConfig2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/2/3 10:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestControllerTest {


    @Autowired
    private RedisConfig2 redisConfig2;

    @Test
    public  void test1(){
        StringRedisTemplate redisTemplateByDb = redisConfig2.getRedisTemplateByDb(2);
        Set<String> keys = redisTemplateByDb.keys("*_*");
        boolean contains = keys.contains("167_225_1");
        redisTemplateByDb.opsForValue().set("167_225_1","sss",7200,TimeUnit.SECONDS);
        redisTemplateByDb.opsForValue().set("169_332_1","ss",7200,TimeUnit.SECONDS);
//        redisTemplateByDb.opsForValue().
//
    }
}