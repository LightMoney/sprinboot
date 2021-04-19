package cn.fan.testfunction.service.impl;

import cn.fan.testfunction.service.Fighter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 默认spring是通过无参构造去创建bean的
 * 如果同时存在有参，还是用无参
 * 只有一个有参，才会去调用
 * 没有无参，有多个有参构造，会报错，spring不知道使用哪一个  可以添加@autowired指定，就不会报错
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/19 11:04
 */
@Service
public class UserService {
    private String id;

    @Autowired
    private OderService oderService;  //by type    by name
//    public UserService() {
//        System.out.println("1");
//    }

    public UserService(OderService oderService) {//by type   by name

        System.out.println("2");
        this.oderService = oderService;
    }

    @Autowired
    public UserService(OderService oderService,OderService oderService1) {

        System.out.println("3");
    }
}
