package cn.fan.testfunction.service.impl;

import cn.fan.testfunction.service.Fighter;
import org.springframework.stereotype.Service;

//@Service
public class Billy implements Fighter {
    @Override
    public void fight() {
        System.out.println("Billy：吾乃新日暮里的王，三界哲学的主宰。");
    }
}
