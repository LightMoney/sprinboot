package cn.fan.testfunction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Van {
    @Autowired
    private Fighter fighter2;

    public void fight(){
        System.out.println("van：boy next door,do you like 玩游戏");
        fighter2.fight();
    }
}