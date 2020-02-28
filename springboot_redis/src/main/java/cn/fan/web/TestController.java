package cn.fan.web;

import cn.fan.result.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(value = "测试")
public class TestController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "set方法", notes = "redis测试")
    @RequestMapping("/set")
    public ResponseResult set(@ApiParam(value = "值", required = true) @RequestParam String value) {
        redisTemplate.opsForValue().set("key", value);
        return new ResponseResult(true,true);
    }
    @ApiOperation(value = "get方法",notes = "redis测试")
    @RequestMapping("/get")
    public  ResponseResult get(@ApiParam(value = "键", required = true) @RequestParam String key){

        return new ResponseResult(true,redisTemplate.opsForValue().get(key));
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
        redisTemplate.opsForZSet().add(key,value,num);
        return new ResponseResult(true,true);
    }
}
