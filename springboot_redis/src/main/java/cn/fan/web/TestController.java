package cn.fan.web;


//import cn.fan.config.RedisConfig2;
//import cn.fan.config.RedisConfig2;
import cn.fan.config.RedisConfig2;
import cn.fan.model.PositionData;
import cn.fan.result.ResponseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 不配置序列化方式 在redis里看到的是乱码，只有查出来才能看见
 * 使用stringRedistempalte也行
 */

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
    public ResponseResult set(@ApiParam(value = "值", required = false) @RequestParam String value) throws JsonProcessingException {
//        redisTemplate.opsForValue().set("key", value);
//        redisConfig.getRedisTemplateByDb(0).opsForValue().set("key", value,100, TimeUnit.SECONDS);

//        redisConfig.getRedisTemplateByDb(0).opsForHash().put("t3","t4","tt");
//        redisConfig.getRedisTemplateByDb(0).expire("t3",180,TimeUnit.SECONDS);
        PositionData data = new PositionData();
        data.setTaskId("WQ119202011031421");
        data.setLongitude(104.06476521);
        data.setLatitude(30.55180102);
        data.setCreateTime(System.currentTimeMillis());
        PositionData data1 = new PositionData();
        data1.setTaskId("CX20201026140021");
        data1.setLongitude(104.06476521);
        data1.setLatitude(30.55180102);
        data1.setCreateTime(System.currentTimeMillis());
        redisConfig.getRedisTemplateByDb(2).opsForValue().set("100032:WQ119202011031421",new ObjectMapper().writeValueAsString(data));
        redisConfig.getRedisTemplateByDb(2).opsForValue().set("100032:CX20201026140021",new ObjectMapper().writeValueAsString(data1));
//        redisConfig.getRedisTemplateByDb(2).opsForValue().set("100031teste",data.toString());
//        redisTemplate.opsForValue().set("key", value);
        return new ResponseResult(true,true);
    }
    @ApiOperation(value = "get方法",notes = "redis测试")
    @RequestMapping("/get")
    public  ResponseResult get(@ApiParam(value = "键", required = true) @RequestParam String key) throws IOException {
//        return new ResponseResult(true,redisTemplate.opsForValue().get(key));
//        String s = redisConfig.getRedisTemplateByDb(2).opsForValue().get("100032teste");

//        PositionData data=new ObjectMapper().readValue(s,PositionData.class);
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
