package cn.fan.service;

import javax.jws.WebService;

/**
 * Auth Mr.fan
 * Date 2020/1/13 15:33
 * 创建服务接口
 **/
@WebService(name = "DemoService",//暴露的服务名
        targetNamespace = "http://service.fan.cn"//命名空间，一般是报名倒序
)
public interface DemoService {
    public String sayHello(String user);
}
