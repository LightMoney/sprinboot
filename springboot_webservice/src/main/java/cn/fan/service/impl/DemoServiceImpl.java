package cn.fan.service.impl;

import cn.fan.service.DemoService;

import javax.jws.WebService;
import java.util.Date;

/**
 * Auth Mr.fan
 * Date 2020/1/13 15:39
 * 创建服务端口实现类
 **/
@WebService(serviceName = "DemoService",//与接口一致
        targetNamespace = "http://service.fan.cn",//与接口一致
        endpointInterface = "cn.fan.service.DemoService"//接口地址
)
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String user) {
        return user + "时间：" + new Date() + "";
    }
}
