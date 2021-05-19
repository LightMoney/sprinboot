package cn.fan;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.List;


public class ShiroTest {

    /**
     * 测试认证
     */
    @Test
    public void testLogin() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_test.ini");
        SecurityManager instance = factory.getInstance();
        SecurityUtils.setSecurityManager(instance);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("colin", "123456");
        subject.login(token);
        System.out.println("是否登录成功：" + subject.isAuthenticated());
        System.out.println(subject.getPrincipal());
    }

    /**
     * 测试授权
     */
    @Test
    public void testLogin2() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_test2.ini");
        SecurityManager instance = factory.getInstance();
        SecurityUtils.setSecurityManager(instance);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("back", "1234");
        subject.login(token);
//登录成功以后授权
//        授权：检验是否拥有角色 是否拥有权限
        System.out.println(subject.hasRole("role1"));
        System.out.println(subject.isPermitted("user:save"));

    }

    /**
     * 测试自定义的realm
     */
    @Test
    public void testLogin3() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_test3.ini");
        SecurityManager instance = factory.getInstance();
        SecurityUtils.setSecurityManager(instance);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("back", "1234");
//        这个会自己去找realm
        subject.login(token);
//登录成功以后授权
//        授权：检验是否拥有角色 是否拥有权限
        System.out.println(subject.hasRole("role1"));
        System.out.println(subject.isPermitted("user:save"));

    }
}
