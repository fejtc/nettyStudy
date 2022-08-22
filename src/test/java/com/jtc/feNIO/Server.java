package com.jtc.feNIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.jtc.nettytest.ByteBufferUtil.debugRead;

//非阻塞模式开发时不怎么用
@Slf4j(topic = "c.Server")
public class Server {
    public static void main(String[] args) throws IOException {
        //使用nio来理解阻塞模式，单线程
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //1、创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//非阻塞模式
        //2、绑定监听窗口
        ssc.bind(new InetSocketAddress(8080));
        //3、连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while(true){
            //4、accept建立与客户端连接，SocketChannel用来与客户端之间通信
            SocketChannel sc = ssc.accept();//非阻塞，线程还会继续运行
            if(sc != null){
                log.debug("connecting...{}",sc);
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for(SocketChannel channel : channels){
                int read = channel.read(buffer);//非阻塞，线程仍然会继续执行
                if(read > 0){
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read...{}",channel);
                }
            }

        }
    }
}
