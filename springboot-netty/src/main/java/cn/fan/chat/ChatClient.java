package cn.fan.chat;

import cn.fan.demo.DemoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/29 11:10
 */
public class ChatClient {

    public static void main(String[] args) {

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);

            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast("decoder",new StringDecoder())
                            .addLast("endcoder",new StringEncoder())
                            .addLast(new ChatClientHandler());
                }
            });

            ChannelFuture f = b.connect("127.0.0.1", 8835).sync();

            Channel channel = f.channel();
            Scanner  sc=new Scanner(System.in);
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                channel.writeAndFlush(s);
            }

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }


    }
}
