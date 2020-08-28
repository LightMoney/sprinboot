package cn.fan.testfunction;

import cn.fan.testfunction.utils.OnlyIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public class DesignPatternTest {
    @Test
    public void remove() {
        HashMap map = new HashMap();
        map.put("key", "1");
        map.put("key1", "2");
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        new TreeSet<>(new ArrayList<>());
        new ArrayList<>(new TreeSet<>()).toArray();

    }

    @Test
    public void converTo() {
        //数组转list
        String[] a = {"a", "b", "c"};
        List<Object> objects = Arrays.asList(a);
        //list转数组
        new ArrayList<>().toArray();
        ArrayList<Object> objects1 = new ArrayList<>(10);
        objects1.add("test");
        objects1.add("test2");
        objects1.add("test3");
        objects1.add(2,"good");
objects.add(true);
        Assert.notNull(objects1);

        LinkedBlockingDeque<Object> objects2 = new LinkedBlockingDeque<>();
        objects2.add("1");
        objects2.add("2");
//保证集合不可修改
Collections.unmodifiableCollection(new ArrayList<>());


    }

    @Test
    public void  test1(){
        int  a=2;
        int  b=3;
        if (a==2){
            System.out.println(1);
        }else if (a==2 &&b==3){
            System.out.println(3);
        }

        String encode = new BCryptPasswordEncoder().encode("123456");
        boolean matches = new BCryptPasswordEncoder().matches("123456", encode);


    }
    @Test
    public  void test2(){
        //电子邮件
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher("123456@4654.com");
        boolean isMatched = matcher.matches();
        System.out.println(isMatched);
    }

    @Test
    public  void test3(){
        String xx = OnlyIdUtils.generate("xx");
        log.info(xx);
    }
}
