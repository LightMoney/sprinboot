package cn.fan.testfunction;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.*;

public class DesignPatternTest {
    @Test
    public  void  remove(){

        new TreeSet<>(new ArrayList<>());
        new ArrayList<>(new TreeSet<>()).toArray();
    }
}
