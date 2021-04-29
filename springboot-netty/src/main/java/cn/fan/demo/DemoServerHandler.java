package cn.fan.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;

/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/29 10:04
 */
public class DemoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端建立连接完成");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest){

            System.out.println("收到客户端消息："+((HttpRequest) msg).uri());
        }else {
            ByteBuf msg1 = (ByteBuf) msg;
            System.out.println("收到客户端消息："+msg1.toString(CharsetUtil.UTF_8));
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端数据读取完成");
    }
}
