package cn.fan.springboot_mail;


import cn.fan.springboot_mail.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
 public class SpringbootMailApplicationTests {
@Autowired
private JavaMailSender mailSender;

    @Test
   public void contextLoads() {
       new  MailUtil(mailSender).sendSimpleMail("746329807@qq.com","13881983613@163.com","测试","测试");
    }

}
