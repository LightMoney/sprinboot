package cn.fan.testfunction.thread;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 模拟线程池框架
 * 这里采用的是jdk  jul ThreadPoolExecutor
 */
public class TaskExecutor {
    /**
     *cpu密集型 N+1   一些内存操作
     * IO密集型 2N  网关类的微服务
     */
    ////这里模拟创建定常队列，然后设置了无界队列长度，避免耗尽资源（存活时间可以设为0）
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            10, 10, 60,
            TimeUnit.SECONDS, new LinkedBlockingDeque(20), new ThreadPoolExecutor.DiscardPolicy());

    public  void  execute(){
        for(int i=0;i<3;i++) {
            poolExecutor.execute(new TaskMethod());
        }
    }
}
