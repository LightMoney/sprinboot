package cn.fan.testfunction;

import cn.fan.testfunction.config.AopBeanConfig;
import cn.fan.testfunction.model.*;
import cn.fan.testfunction.service.TestService;
import cn.fan.testfunction.utils.MessageUtils;
import cn.fan.testfunction.utils.OnlyIdUtils;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.thread.RejectPolicy;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import sun.plugin.security.JDK11ClassFileTransformer;
import sun.rmi.runtime.Log;

import java.awt.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
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
        List list = new ArrayList();
        for (int i = 100; i < 1000; i++) {
            int a = i / 100;
            int b = (i % 100) / 10;
            int c = i % 10;
            int d = a * a * a + b * b * b + c * c * c;
            if (i == d) {
                list.add(i);
            }
        }
        log.info(list.toString());
    }

    /**
     * 将一个正整数分解质因数。例如：输入90,打印出90=233*5。
     */
    @Test
    public void testFJ() {
        int n = 37;
        int k = 2;
        while (n >= k) {
            if (n == k) {
                log.info("" + k);
                break;
            } else if (n % k == 0) {
                log.info("" + k);
                n = n / k;
            } else {
                k++;
            }

        }
    }

    /**
     * 利用条件运算符的嵌套来完成此题：学习成绩> =90分的同学用A表示，60-89分之间的用B表示，60分以下的用C表示。
     */
    @Test
    public void testC() {
        int scot = 50;
        String cj = scot >= 90 ? "A" : scot >= 60 ? "B" : "C";
        log.info(cj);
    }

    /**
     * 输入两个正整数m和n，求其最大公约数和最小公倍数
     */
    @Test
    public void testG() {

    }

    @Test
    public void testT() {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        ExecutorService executorService = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        Executors.newSingleThreadScheduledExecutor();
        Executors.newSingleThreadExecutor();
        Executors.newWorkStealingPool();    //jdk 1.8才有
//new ThreadPoolExecutor(1,1,9,TimeUnit.SECONDS,new LinkedBlockingDeque<>(), new java.util.concurrent.ThreadPoolExecutor.AbortPolicy());
//        executorService.execute(()->{
//            System.out.println("任务被执行,线程:" + Thread.currentThread().getName());
//        });
        System.out.println("开始执行");
        // 创建任务
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("任务被执行,线程:" + Thread.currentThread().getName());
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };

        scheduledExecutorService.schedule(() -> {
            System.out.println("任务被执行,线程:" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
        }, 2, TimeUnit.SECONDS);

        // 线程池执行任务(一次添加 4 个任务)
        // 执行任务的方法有两种:submit 和 execute
//         threadPool.submit(runnable);// 执行方式 1:submit     单参数为callable时才有返回值 否则为null
//        threadPool.execute(runnable); // 执行方式 2:execute
//        threadPool.execute(runnable);
//        threadPool.execute(runnable);
        System.out.println("结束");
    }

    /**
     * 异步编程    jdk5   Future
     * 方便简单  但是对复杂的多计算合并操作
     * 实际中我们需要实现如下功能  使用future就不方便
     * 1.将多个异步计算的结果合并成一个
     * 2.等待Future集合中的所有任务都完成
     * 3.Future完成事件（即，任务完成以后触发执行动作）
     */
    @Test
    public void testTT() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> submit = executorService.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        });
        System.out.println(submit.get());
        System.out.println("finish!!!");
    }

    /**
     * 注意  如果每一步都很简单没有必要 使用异步   使用场景是每一步都需要大量时间的操作
     * jdk 8  CompletableFuture
     * 1.在Java8中，CompletableFuture提供了非常强大的Future的扩展功能，可以帮助我们简化异步编程的复杂性，并且提供了函数式编程的能力，可以通过回调的方式处理计算结果，也提供了转换和组合 CompletableFuture 的方法。
     * 2.它可能代表一个明确完成的Future，也有可能代表一个完成阶段（ CompletionStage ），它支持在计算完成以后触发一些函数或执行某些动作。
     * 3.它实现了Future和CompletionStage接口
     */
    @Test
    public void testCC() {

//        CF<Stirng> cf = CompletableFuture.completableFuture("Value");
//        String result = cf.get();
//        // 阻塞等待结果
//        String result = cf.join();
//
//// 非阻塞等待结果输出
//        cf.thenAccept(s -> System.out.println(s));
//
//        String load() {...}
//// 非阻塞等待结果
//        CF<Stirng> cf = CompletableFuture.supplyAsync(() -> load());
//// 非阻塞等待结果，并且指定使用某个线程池执行
//        CF<Stirng> cf = CompletableFuture.supplyAsync(() -> load() , executorService);
        //带有async的方法都是默认异步执行  不在当前线程内执行
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenApply(s -> s + "Word")
                .thenApply(String::toLowerCase)
                .thenCombine(CompletableFuture.completedFuture("Java"), (s1, s2) -> s1 + s2)
                .thenAccept(System.out::println);
    }

    @Test
    public void tee() {
        List<String> phone = new ArrayList<>(2);
        phone.add("13881983613");
        phone.add("18502821590");
        phone.forEach(p -> {
            MessageUtils.send(p, "tweq2");
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

    /**
     * 观察者模式
     * 观察者模式的优点是为了给观察目标和观察者解耦，而缺点也很明显，从上面的例子也可以看出，如果观察者对象太多的话，有可能会造成内存泄露。
     * 另外，从性能上面考虑，所有观察者的更新都是在一个循环中排队进行的，所以观察者的更新操作可以考虑做成线程异步（或者可以使用线程池）的方式，以提升整体效率。
     */
    @Test
    public void tess() {
        JavaObservable javaStackObservable = new JavaObservable();

        // 添加观察者
        javaStackObservable.addObserver(new ReadObserver("小明"));
        javaStackObservable.addObserver(new ReadObserver("小张"));
        javaStackObservable.addObserver(new ReadObserver("小爱"));

        // 发表文章
        javaStackObservable.publish("什么是观察者模式？");

    }

    /**
     * future模式   去除了主函数中的等待时间，并使原本需要等待的时间可以处理其他业务
     */
    @Test
    public void testsF() throws ExecutionException, InterruptedException {
        FutureTask future = new FutureTask<>(new RealData("a"));
        ExecutorService executor = Executors.newFixedThreadPool(1);
//执行FutureTask/
//在这里开始线程进行RealData的call执行
        executor.submit(future);
        System.err.println("请求完毕");
        try {
//这里依然可以做额外的数据操作.这里使用sleep代替其他业务逻辑的处理
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
//此时call()方法没有执行完成,则依然会等待
        System.err.println("数据=" + future.get());

//while (future.isDone()) {

//}

//其他一些常用的方法
//future.cancel(boolean mayInterruptIfRunning)
//future.isCancelled();
//future.isDone();

    }

    /**
     * 判断是否是回文
     * 思路：取头尾索引依次对比，跳过非字母数字
     */
    @Test
    public void testHuiWen() {
        boolean palindrome = isPalindrome("A man, a plan, a canal: Panama");
        log.info(palindrome + "");
    }

    public boolean isPalindrome(String s) {
        // write your code here
        if (s == null || s.length() == 0) {
            return true;
        }

        s = s.toLowerCase();
        int i = 0, j = s.length() - 1;

        while (i < j) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i))) {
                i++;
            }
            while (i < j && !Character.isLetterOrDigit(s.charAt(j))) {
                j--;
            }
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }


    /**
     * 写一个函数实现从字符串中删除任意给出的字符
     */
    @Test
    public void testDelete() {
        String targ = "fslfsdfsd";
        String flag = "f";
        String replace = targ.replace(flag, "");
//        String trim = replace.trim();
        log.info(replace);
    }

    /**
     * 如何从一个字符串中找出第一个非重复的字符
     */
    @Test
    public void testFirstDuplicat() {
        String a = "abdacfffd";
        char[] chars = a.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length; i++) {
            int x = 0;
            for (int j = 0; j < length; j++) {
                if (chars[i] == chars[j]) {
                    ++x;
                }
            }
            if (x < 2) {
                System.out.println(chars[i]);
                break;
            }

        }
    }

    /**
     * 如何计算一个给定的字符在字符串中出现的次数
     */
    @Test
    public void testNum() {
        String s = "agvdgdsfssfsd";
        char a = 's';
        int x = 0;
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (a == chars[i]) {
                x++;
            }
        }
        System.out.println(x);
    }

    /**
     * 写出一个函数判断两个字符串是否可以通过改变字母的顺序变成一样的字符串
     */

    @Test
    public void testDec() throws ClassNotFoundException, NoSuchMethodException {
        ApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AopBeanConfig.class);
//        Class<?> user = Class.forName("cn.fan.testfunction.web.TestColntroller");
//
//        Method setService = user.getMethod("setService", TestService.class);
//       setService.invoke(user, )
//        System.out.println("ss");


    }

    public boolean anagram(String s, String t) {
        // write your code here
        int[] cntS = new int[256];
        int[] cntT = new int[256];
        for (char c : s.toCharArray()) {
            cntS[c]++;
        }
        for (char c : t.toCharArray()) {
            cntT[c]++;
        }
        for (int i = 0; i < 256; i++) {
            if (cntS[i] != cntT[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 冒泡排序
     */
    public void testMP(){
        int[] a = {1, 2, 5, 4, 3};
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i-1; j++) {
                if (a[j] > a[j + 1]) {
                    int c = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = c;
                }
            }
        }
    }
}
