package cn.fan.testfunction.thread;

public class TaskMethod implements Runnable {

    @Override
    public void run() {
        System.out.println(""+Thread.currentThread().getName());
    }
}
