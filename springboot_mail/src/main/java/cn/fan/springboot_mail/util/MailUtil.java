package cn.fan.springboot_mail.util;


import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @描述 ： 邮件工具
 * @Author :  zlc
 * @Date : 2019/10/10 16:41
 **/
@Component
public class MailUtil {
    
    private final JavaMailSender mailSender;

    
    public MailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;

    }

    public void sendSimpleMail(String from,String to,String subject, String content) {
        // new 一个简单邮件消息对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 和配置文件中的的username相同，相当于发送方
        message.setFrom(from);
        // setCc 抄送，setTo 发送，setBCc密送
        message.setCc(to);
        message.setSubject("【类型】：【文本】-【来源】【" + "测试" + "】-主题【" + subject + "】");
        // 正文
        message.setText(content);
        // 发送
        mailSender.send(message);
    }

    public void sendHtmlMail(String from,String to,String subject, String content) {

        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("【类型】:【HTML】【来源】-【" + "测试" + "】-主题【" + subject + "】");
            //true代表支持html
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
           // logger.error("发送HTML邮件失败：", e);
        }
    }

    public void sendAttachmentMail(String from,String to,String subject, String content, File file) {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("【类型】:【附件】-【来源】-【" + "测试" + "】-主题【" + subject + "】");
            helper.setText(content, true);
            FileSystemResource file1 = new FileSystemResource(file);
            String fileName = file1.getFilename();
            //添加附件，可多次调用该方法添加多个附件  
            helper.addAttachment(fileName, file1);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendInlineResourceMail(String from,String to,String subject, String content, String rscPath, String rscId) {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("【类型】:【图片】-【来源】-【" + "测试" + "】-主题【" + subject + "】");
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            //重复使用添加多个图片
            helper.addInline(rscId, res);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}

