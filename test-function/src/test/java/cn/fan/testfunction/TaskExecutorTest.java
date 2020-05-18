package cn.fan.testfunction;


import cn.fan.testfunction.thread.TaskExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskExecutorTest {
    @Bean
    private TaskExecutor taskExecutor() {
        return new TaskExecutor();
    }

    ;

    @Test
    public void contextLoads() {
        taskExecutor().execute();
    }
}