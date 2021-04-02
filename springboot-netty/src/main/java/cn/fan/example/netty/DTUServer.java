//package cn.fan.example.netty;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.DelimiterBasedFrameDecoder;
//import io.netty.handler.timeout.ReadTimeoutHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Component;
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
// * @Description : 恒远物联 TCP 协议的 DTU 服务器
// * ---------------------------------
// * @Author : zsq
// * @Date : 2019/1/18
// */
//@Component
//@PropertySource("classpath:netty.properties")
//public class DTUServer {
//
//    private final Logger logger = LoggerFactory.getLogger(DTUServer.class);
//
//    private static final int READ_IDEL_TIME_OUT = 30; // 读超时,这里只需要读取故其余两个超时不做处理,单位秒
//    private static final int WRITE_IDEL_TIME_OUT = 30;// 写超时
//    private static final int ALL_IDEL_TIME_OUT = 30; // 所有超时
//
//    @Autowired
//    private DTUMessageHandler dtuMessageHandler;
//
//    // 服务器端口
//    @Value("${port}")
//    private Integer port;
//    // 数据结束标志
//    private static final byte[] DATA_FLAG = {0X55};
//
//    // 处理已经被接收的连接（子）
//    private EventLoopGroup childGroup;
//    // 接收进来的连接(父)
//    private EventLoopGroup parentGroup;
//    // netty 启动辅助类
//    private ServerBootstrap sbs;
//    //
//    private ChannelFuture future;
//
//    public DTUServer() {
//        //父子均采用 reactor 多线程模型，默认线程数为 cpu 核数*2
//        parentGroup = new NioEventLoopGroup();
//        childGroup = new NioEventLoopGroup();
//        sbs = new ServerBootstrap();
//    }
//
//    public void start() {
//        logger.info("==========================netty 服务器正在启动，端口为" + this.port + "=============================");
//        // 设置父子线程组
//        sbs.group(parentGroup, childGroup);
//        sbs.channel(NioServerSocketChannel.class);
//        // 设置子线程组的处理器
//        sbs.childHandler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            protected void initChannel(SocketChannel socketChannel) throws Exception {
//                ChannelPipeline pipeline = socketChannel.pipeline();
//                // 分割符解码器,将数据以 0x55 字节分隔开，若要在原数据中保留分隔符，可以在中间添加一个boolean 类型的参数，设为 false，默认不保留分隔符
//                pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(DATA_FLAG)));
//                // 加入自定义解码器，还原原始数据，这里不在分割时保留分隔符是因为 末尾也是 0x55，保留可能会出现问题
//                pipeline.addLast(new DTUDecoder());
//                // 超时检测
//                pipeline.addLast(new ReadTimeoutHandler(READ_IDEL_TIME_OUT));
//                // 加入自定义数据处理器
//                pipeline.addLast(dtuMessageHandler);
//            }
//        });
//        // ChannelOption的参数配置并不是netty自己的，而是socket自身的
//
//        // 给父线程组设置可选参数
//        // 当服务器请求处理线程满时，用于临时存放已完成三次握手请求的队列的最大长度，若未设置或者值小于1，java自动设为50
//        sbs.option(ChannelOption.SO_BACKLOG, 1024);
//        // 给子线程组设置可选参数
//        sbs.childOption(ChannelOption.SO_KEEPALIVE, true);
//        // 绑定端口
//        future = sbs.bind(port);
//    }
//
//}
