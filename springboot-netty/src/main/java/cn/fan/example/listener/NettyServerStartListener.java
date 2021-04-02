//package cn.fan.example.listener;
//
//
//import com.hthl.iot.gateway.tcp.dtu.netty.DTUServer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
///**
// * code is far away from bug with the animal protecting
// * ┏┓　 ┏┓
// * ┏┛┻━━━┛┻┓
// * ┃　　　　　　┃
// * ┃　　　━　　┃
// * ┃　┳┛　┗┳　┃
// * ┃　　　　　　┃
// * ┃　　　┻　　┃
// * ┃　　　　　　┃
// * ┗━┓　　　┏━┛
// * 　　┃　　　┃神兽保佑
// * 　　┃　　　┃代码无BUG！
// * 　　┃　　　┗━━━┓
// * 　　┃　　　　　 ┣┓
// * 　　┃　　　　　 ┏┛
// * 　　┗┓┓ ┏━┳┓ ┏┛
// * 　　　┃┫┫　┃┫┫
// * 　　　┗┻┛　┗┻┛
// *
// * @Description :
// * ---------------------------------
// * @Author : zsq
// * @Date : 2019/1/9
// */
//@Component
//public class NettyServerStartListener implements ServletContextListener {
//
//    private final Logger logger = LoggerFactory.getLogger(NettyServerStartListener.class);
//
//    @Autowired
//    private DTUServer server;
//
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        logger.info("==================netty 监听器正在启动=======================");
//        new Thread(() ->{
//            server.start();
//        }).start();
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//    }
//}
