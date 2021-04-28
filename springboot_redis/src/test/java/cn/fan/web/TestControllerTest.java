package cn.fan.web;


import cn.fan.config.RedisConfig2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
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
//lua脚本
    @Resource
    private DefaultRedisScript<Boolean> redisScript;

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

    @Test
    public   void    testSet(){
        StringRedisTemplate redisTemplateByDb = redisConfig2.getRedisTemplateByDb(0);
//        redisTemplateByDb.opsForValue().set("a","4");
//        redisTemplateByDb.opsForSet().add("test","xoox","xoxo","xxoo","oxox","xoxo");
//        这个是不去重的  .distinctRandomMembers()才去重
        List<String> list = redisTemplateByDb.opsForSet().randomMembers("test", 4);
        System.out.println(list);
//        Boolean test1 = redisTemplateByDb.opsForSet().isMember("test1", "1");
//        System.out.println(test1+"");
//        redisTemplateByDb.opsForSet().remove("test1","1");
//        List<String> keys = Arrays.asList("testLua", "hello lua");
//        Boolean execute = redisTemplateByDb.execute(redisScript, keys, "10000");
//        System.out.println(execute+"");
    }
}