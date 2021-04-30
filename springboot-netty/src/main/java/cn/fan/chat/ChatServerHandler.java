package cn.fan.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * TODO
 *
 * @author HTHLKJ
 * @version 1.0
 * @date 2021/4/29 11:11
 */
public class ChatServerHandler extends ChannelInboundHandlerAdapter {
    //GlobalEventExecutor.INSTANCE全局事件执行器
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush("[客户端]：" + channel.remoteAddress() + "上线了 " + sdf.format(new Date()));
        channels.add(channel);
        System.out.println("客户端：" + channel.remoteAddress() + "上线了 " + sdf.format(new Date()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        channels.forEach(p -> {
            if (p != channel) {
                p.writeAndFlush("[客户端]:" + p.remoteAddress() + "发送消息:" + msg);
            } else {
                p.writeAndFlush("[自己]：发送消息:" + msg);
            }
        });
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }

    //下线通知
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.remove(channel);
        System.out.println("" + channel.remoteAddress() + "下线");
    }
}
