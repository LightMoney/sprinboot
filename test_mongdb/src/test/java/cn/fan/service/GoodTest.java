package cn.fan.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GoodTest {
    @Test
    public void test(){
        short[] s={1,3,5};
        List<short[]> collect = Stream.of(s).collect(Collectors.toList());
        System.out.println(collect);
    }
}
