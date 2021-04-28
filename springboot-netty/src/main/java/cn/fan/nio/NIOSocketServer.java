package cn.fan.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * nio  新io或非阻塞io
 */
public class NIOSocketServer {
    public static void main(String[] args) throws IOException {
        //创建serversocketChannel 实例
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.socket().bind(new InetSocketAddress(8086));
//设置为非阻塞
        socketChannel.configureBlocking(false);
//        打开selector处理channel  创建epoll
        Selector selector = Selector.open();
        //serversocketchannel 注册到selector上
        SelectionKey register = socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功。。。。。");


        while (true) {
            //阻塞等待需要处理的事情发生
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
//遍历事件对事件进行处理
            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();
                //如果时accpect事件就获取连接和事件注册
                if (key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    //这里只注册了读事件，如果要写数据给客户端 这里可以注册写事件
                    SelectionKey register1 = accept.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");
                }else if (key.isReadable()){

                    SocketChannel  socketChannel1=(SocketChannel)key.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(128);
                    int read = socketChannel1.read(allocate);
                    if (read>0){
                        System.out.println(new String(allocate.array()));
                    }else if (read==-1){
                        //客户端断开连接
                        socketChannel1.close();
                    }
                }
                //移除本次事件的key，避免被select重复处理
                iterator.remove();
            }

        }
    }
}
