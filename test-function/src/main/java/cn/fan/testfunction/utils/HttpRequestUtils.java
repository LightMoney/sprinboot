package cn.fan.testfunction.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lgh
 * @date 2019/01/29 0029
 * @description
 */
public class HttpRequestUtils {

    public void testRequest() {
        // 请求地址
        String url = "http://172.18.100.27:8083/site/getSite";

        // 访问参数，没有不用put添加值
        Map<String, String> map = new HashMap();
        map.put("deviceName", "温度计");

        // 请求类型（post,put,get）
        String requestType = "post";

        // 结果编码
        String encoding = "UTF-8";

        //提交请求
        String result = jsonRequestTool(url, map, requestType, encoding);
        System.out.println(result);
    }

    /**
     * json请求数据访问工具(支持post,put,get)
     *
     * @param url         请求url
     * @param mapPair     参数map
     * @param requestType 请求类型
     * @param encoding    结果编码
     * @return
     */
    public static String jsonRequestTool(String url, Map<String, String> mapPair, String requestType, String encoding) {
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //创建请求父对象
            HttpUriRequest httpUriRequest = null;
            if ("post".equals(requestType.toLowerCase().trim())) {
                httpUriRequest = new HttpPost(url);
            }
            if ("get".equals(requestType.toLowerCase().trim())) {
                httpUriRequest = new HttpGet(url);

            }
            if ("put".equals(requestType.toLowerCase().trim())) {
                httpUriRequest = new HttpPut(url);
            }

            // 请求地址
            System.out.println("请求地址：" + url);

            // post,put请求body参数添加
            if (!"get".equals(requestType.toLowerCase().trim()) && mapPair != null && !mapPair.isEmpty()) {
                //装填参数
                String jsonString = JSONObject.toJSONString(mapPair);
                StringEntity s = new StringEntity(jsonString, encoding);
                if (httpUriRequest instanceof HttpPost) {
                    HttpPost post = (HttpPost) httpUriRequest;
                    //设置参数到请求对象
                    post.setEntity(s);
                    httpUriRequest = post;
                } else {
                    HttpPut put = (HttpPut) httpUriRequest;
                    //设置参数到请求对象
                    put.setEntity(s);
                    httpUriRequest = put;
                }
                System.out.println("请求参数：" + jsonString);
            }

            //设置header信息
            //指定报文头【Content-type】、【User-Agent】
            httpUriRequest.setHeader("Content-type", "application/json");
            httpUriRequest.setHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDg3NzYwODIsInVzZXJfbmFtZSI6IjEzOTE3MTcyNzM5IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjMxYmMxMjFlLTJkMmItNGE0Ni05MDVjLTE2YjQxMTc4Zjc1MSIsImNsaWVudF9pZCI6InJvb3QiLCJzY29wZSI6WyJhbGwiXX0.FYnHRFRw_MHxz9orVONHgNL1xTJVRqCB_DzpgH8KgJw");
            httpUriRequest.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = client.execute(httpUriRequest);

            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                return EntityUtils.toString(entity, encoding);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "NULL:Nothing";
    }

}

