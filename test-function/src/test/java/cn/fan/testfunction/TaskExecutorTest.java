package cn.fan.testfunction;


import cn.fan.testfunction.thread.TaskExecutor;
import cn.fan.testfunction.thread2.TestTaskExcutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class TaskExecutorTest {

//   @Bean
//    private TestTaskExcutor excutor(){
//       return new TestTaskExcutor();
//   };

@Autowired
private TestTaskExcutor excutor;
    @Bean
    private TaskExecutor taskExecutor() {
        return new TaskExecutor();
    }



    @Test
    public void contextLoads() {
        taskExecutor().execute();
    }

    @Test
    public  void  testTask() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        for (int i =1;i<=12;i++){
            Thread.sleep(1000);
            list.add(null);
        }
       log.info("开始执行多线程任务1111111111:::"+System.currentTimeMillis());
        for (int i =0;i<=5;i++){
            excutor.doTaskTest(i);
        }
        log.info("主线程继续执行222222222222222:::::"+Thread.currentThread().getName());

    }
}