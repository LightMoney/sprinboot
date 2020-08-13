package cn.fan.testfunction.service.impl;

import cn.fan.testfunction.service.Fighter;
import org.springframework.stereotype.Service;

//@Service
public class Babana implements Fighter {
    @Override
    public void fight(){
        System.out.println("Banana: 自由的气息，蕉迟但到");
    }
}