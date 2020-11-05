package cn.fan.web;


//import cn.fan.config.RedisConfig2;
import cn.fan.config.RedisConfig2;
import cn.fan.result.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
@Api(value = "测试")
public class TestController {
//    单数据源方式 rediscongfig
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

//    多数据源  redisconfig2
@Autowired
private RedisConfig2 redisConfig;

//   多数据源 使用的是springbootconfig的配置
//    /**
//     * 不写默认使用带有@Primary的RedisTemplate
//     */
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Autowired
//    @Qualifier("redisTemplate2")
//    private RedisTemplate redisTemplate2;



    @ApiOperation(value = "set方法", notes = "redis测试")
    @RequestMapping("/set")
    public ResponseResult set(@ApiParam(value = "值", required = false) @RequestParam String value) {
//        redisTemplate.opsForValue().set("key", value);
//        redisConfig.getRedisTemplateByDb(0).opsForValue().set("key", value,100, TimeUnit.SECONDS);
        redisConfig.getRedisTemplateByDb(0).opsForHash().put("t3","t4","tt");
        redisConfig.getRedisTemplateByDb(0).expire("t3",180,TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set("key", value);
        return new ResponseResult(true,true);
    }
    @ApiOperation(value = "get方法",notes = "redis测试")
    @RequestMapping("/get")
    public  ResponseResult get(@ApiParam(value = "键", required = true) @RequestParam String key){
//        return new ResponseResult(true,redisTemplate.opsForValue().get(key));
        return ResponseResult.FAIL();
    }

    /**
     * zset内部会根据scope进行排序，越小越上面
     * @param key
     * @param value
     * @param num
     * @return
     */
    @ApiOperation(value = "zset方法", notes = "redis测试")
    @RequestMapping("/zset")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "value",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "num",dataType = "Integer",paramType = "query")
    })
    public ResponseResult zSet(@RequestParam String key, @RequestParam String value, @RequestParam Integer num) {
//        redisTemplate.opsForZSet().add(key,value,num);
        return new ResponseResult(true,true);
    }
}
