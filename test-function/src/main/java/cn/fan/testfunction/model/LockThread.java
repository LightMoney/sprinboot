package cn.fan.testfunction.model;

/**
 * synchronize测试对象
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/25 9:44
 */
public class LockThread implements Runnable {
    @Override
    public void run() {
        synchronized (this) {
            System.out.println("锁名称：" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "结束");
        }
    }
}
