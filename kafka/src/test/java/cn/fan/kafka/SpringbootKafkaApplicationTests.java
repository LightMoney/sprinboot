package cn.fan.kafka;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootKafkaApplicationTests {

    @Autowired
    private KafkaTemplate template;

    @Test
    public void test() {
        System.out.println("============start=============");
    template.send("TEST_TEST","this is test demo3");
    System.out.println("============success=============");
    }


}
