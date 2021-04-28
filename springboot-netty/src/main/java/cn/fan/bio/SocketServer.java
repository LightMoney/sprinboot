package cn.fan.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *  bio 阻塞io
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("等待连接。。。");
            //阻塞方法  获取不到端口的响应就会阻塞
            Socket client = serverSocket.accept();
            System.out.println("有客户端进入连接");

            handle(client);
        }

    }

    public static void handle(Socket client) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备读。。。");
//阻塞方法  没有数据读时就阻塞
        int read = client.getInputStream().read(bytes);
        System.out.println("d读取完毕。。。。");

        if (read != -1) {
            System.out.println(new String(bytes, 0, read));
        }
    }

}
