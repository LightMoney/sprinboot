package cn.fan.chat;

import cn.fan.demo.DemoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/29 11:10
 */
public class ChatServer {

    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast("decoder",new StringDecoder())
                                    .addLast("endcoder",new StringEncoder())
                                    .addLast(new ChatServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128);
            System.out.println("聊天室服务器启动......");


            ChannelFuture f = b.bind(8835).sync();


            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
