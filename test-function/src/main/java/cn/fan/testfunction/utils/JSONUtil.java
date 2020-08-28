package cn.fan.testfunction.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class JSONUtil {

    public static <T> T parseObject(Object o, Class<T> tClass) {
        String json = JSONObject.toJSONString(o);
        return JSONObject.parseObject(json, tClass);
    }


    public static <T> List<T> parseArrayObject(Object o, Class<T> tClass) {
        String json = JSONObject.toJSONString(o);
        return JSONArray.parseArray(json, tClass);
    }

}
