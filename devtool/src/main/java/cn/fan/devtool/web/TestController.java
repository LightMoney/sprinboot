package cn.fan.devtool.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 热加载还是会需要等待，相当于重启，意义不大
 */
@RestController
public class TestController {
    @GetMapping("/test1")
    public  String testDet(){
        return "good1232";
    }
}
