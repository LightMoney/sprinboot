package cn.fan.testfunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Van {
//    @Autowired
//    @Qualifier("bill")
//    private Fighter fighter2;
    //默认以名字查找注入  bill
    @Resource
    private Fighter bill;
//    private Fighter fighter2;
    public void fight(){
        System.out.println("van：boy next door,do you like 玩游戏");
        bill.fight();
//        fighter2.fight();
    }
}