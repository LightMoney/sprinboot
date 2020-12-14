package cn.fan.testfunction;

import cn.fan.testfunction.model.Student;
import cn.fan.testfunction.model.User;
import cn.fan.testfunction.utils.OnlyIdUtils;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import sun.rmi.runtime.Log;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        objects1.add(2, "good");
        objects.add(true);
        Assert.notNull(objects1);

        LinkedBlockingDeque<Object> objects2 = new LinkedBlockingDeque<>();
        objects2.add("1");
        objects2.add("2");
//保证集合不可修改
        Collections.unmodifiableCollection(new ArrayList<>());


    }

    @Test
    public void test1() {
        int a = 2;
        int b = 3;
        if (a == 2) {
            System.out.println(1);
        } else if (a == 2 && b == 3) {
            System.out.println(3);
        }

        String encode = new BCryptPasswordEncoder().encode("123456");
        boolean matches = new BCryptPasswordEncoder().matches("123456", encode);


    }

    @Test
    public void test2() {
        //电子邮件
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher("123456@4654.com");
        boolean isMatched = matcher.matches();
        System.out.println(isMatched);
    }

    @Test
    public void test3() {
        String xx = OnlyIdUtils.generate("xx");
        log.info(xx);
    }

    //hutool工具类测试  https://www.hutool.cn/
    @Test
    public void test4() {
//        唯一id生成测试
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        log.info("" + id);

        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

    }

    //测试 Optional
    @Test(expected = NoSuchElementException.class)
    public void testOptional() {
        Optional<User> emptyOpt = Optional.empty();
        emptyOpt.get();
    }

    @Test
    public void test() {
        log.info("123".compareTo("32") + "");
    }

    /**
     * 有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
     * //这是一个菲波拉契数列问题
     */
    @Test
    public void testS() {
        log.info("" + f(7));

    }

    int f(int month) {
        if (month == 1 || month == 2) {
            return 1;
        }
        return f(month - 1) + f(month - 2);
    }

    /**
     * 判断101-200之间有多少个素数，并输出所有素数
     * 判断素数：除了一和本身外，不能被整除除
     * 1.从2到当前数-1不整除
     * 2从2到当前数开方 Math.sqrt() 不整除
     */

    @Test
    public void testSS() {

        HashSet set = new HashSet();
        int count = 0;
        for (int i = 101; i <= 200; i++) {
            boolean flag = false;
            for (int j = 2; j < Math.sqrt(i); j++) {
                if ((i % j) == 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                count++;
                set.add(i);
            }
        }
        log.info(set.toString());
    }

    /**
     * 打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。
     * 例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方。
     */
    @Test
    public void testSXH() {
        List list=new ArrayList();
        for (int i = 100; i < 1000; i++) {
            int a = i / 100;
            int b = (i % 100) / 10;
            int c = i % 10;
            int d=a*a*a+b*b*b+c*c*c;
            if (i==d){
                list.add(i);
            }
        }
        log.info(list.toString());
    }
    /**
     * 将一个正整数分解质因数。例如：输入90,打印出90=233*5。
     */
    @Test
    public void testFJ(){
         int n=37;
         int k=2;
         while (n>=k){
             if (n==k){
                 log.info(""+k);
                 break;
             }else if (n%k==0){
                 log.info(""+k);
                 n=n/k;
             }else {
                 k++;
             }

         }
    }

    /**
     * 利用条件运算符的嵌套来完成此题：学习成绩> =90分的同学用A表示，60-89分之间的用B表示，60分以下的用C表示。
     */
    @Test
    public void testC(){
        int scot=50;
        String cj=scot>=90?"A":scot>=60?"B":"C";
        log.info(cj);
    }

    /**
     * 输入两个正整数m和n，求其最大公约数和最小公倍数
     */
    @Test
    public void testG(){

    }
}
