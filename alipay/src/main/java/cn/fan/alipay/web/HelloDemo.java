package cn.fan.alipay.web;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 热部署测试  新项目 启动很快
 */
@Controller
//@RequestMapping("/hello")
public class HelloDemo {
 
    @RequestMapping("/index")
    public String index() {
        return "hello _world!";
    }

    @RequestMapping("/say")
    public String say(){
        return "I love Java!";
    }

    @RequestMapping("/")
    public String toTest(){
        return "index";
    }
}