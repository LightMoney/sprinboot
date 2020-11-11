package cn.fan.testfunction.thread2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 这里使用spring的异步、多线程
 * @Async注解 开启该方法为多线程的异步调用（但线程会重复创建，故和线程池配合使用提升性能ThreadPoolTaskExecutor）
 * 还需要在@SpringBootApplication启动类或者@configure注解类上
 * 添加注解@EnableAsync启动多线程注解
 *
 */
@Slf4j
@Component//方法一定要交给spring管理
public class TestTaskExcutor {
    /**
     *  1，注解的方法必须是public方法
     *
     *   2，方法一定要从另一个类中调用，也就是从类的外部调用，类的内部调用是无效的，因为@Transactional和@Async注解的实现都是基于Spring的AOP，而AOP的实现是基于动态代理模式实现的。那么注解失效的原因就很明显了，有可能因为调用方法的是对象本身而不是代理对象，因为没有经过Spring容器。
     *
     *  3，异步方法使用注解@Async的返回值只能为void或者Future
     * @param i
     */
    @Async("getTaskExector")
    public  void doTaskTest(int i){
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
       log.info("第00" + i + "完成任务，耗时：" + (end - start) + "毫秒，线成名为::" + Thread.currentThread().getName());
    }

    @Bean("getTaskExector")
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int threadCount = Runtime.getRuntime().availableProcessors();//获取到服务器的cpu内核
        executor.setCorePoolSize(threadCount);//核心池大小（实际中通过自定义设置）
        executor.setMaxPoolSize(threadCount);//最大线程数（实际中通过自定义设置）
        executor.setQueueCapacity(1000);//队列程度
        executor.setKeepAliveSeconds(1000);//线程存活空闲时间
        executor.setThreadNamePrefix("tsak-asyn");//线程前缀名称
        //等待任务在关机时完成--表明等待所有线程执行完
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());//配置拒绝策略
        return executor;
    }
}
