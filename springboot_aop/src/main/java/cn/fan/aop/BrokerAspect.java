package cn.fan.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
 
/**
* @desc: 定义切面类：在类上添加@Aspect 和@Component 注解即可将一个类定义为切面类。
 * @Aspect 注解 使之成为切面类
 * @Component 注解 把切面类加入到IOC容器中
* @author: CSH
 *
 * JoinPoint可给方法添加该参数 获取目标方法的对象名称
**/
@Aspect
@Component
public class BrokerAspect {

//    @Before(value = "@annotation(com.hthl.annotation.Dept)")
//@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    /**
     * execution的含义是匹配该包下任意类的任意方法名的任意入参的任意方法返回值
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式（如果想传参数进来，改为有参构造就行）
     */
    @Pointcut("execution(public * cn.fan.controller.AopController.*(..)))")
    public void BrokerAspect(){
 
    }
 
    /**
    * @description  在连接点执行之前执行的通知
    */
    @Before("BrokerAspect()")
    public void doBeforeGame(){
        System.out.println("经纪人正在处理球星赛前事务！");
    }
 
    /**
     * @description  在连接点执行之后执行的通知（返回通知和异常通知的异常）
     */
    @After("BrokerAspect()")
    public void doAfterGame(){
        System.out.println("经纪人为球星表现疯狂鼓掌！");
    }
 
    /**
     * @description  在连接点执行之后执行的通知（返回通知）
     */
    @AfterReturning("BrokerAspect()")
    public void doAfterReturningGame(){
        System.out.println("返回通知：经纪人为球星表现疯狂鼓掌！");
    }
 
    /**
     * @description  在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing("BrokerAspect()")
    public void doAfterThrowingGame(){
        System.out.println("异常通知：球迷要求退票！");
    }
//    /**
//     * @description  使用环绕通知(可以代替上面的方法)
//     */
//    @Around("BrokerAspect()")
//    public void doAroundGame(ProceedingJoinPoint pjp) throws Throwable {
//        try{
//            System.out.println("经纪人正在处理球星赛前事务！");
//            pjp.proceed();
//            System.out.println("返回通知：经纪人为球星表现疯狂鼓掌！");
//        }
//        catch(Throwable e){
//            System.out.println("异常通知：球迷要求退票！");
//        }
//    }

}