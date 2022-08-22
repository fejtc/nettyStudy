package com.jtc.NIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;



@Slf4j(topic = "c.NIOselector")
public class NIOselector {
    public static void main(String[] args) {
        //创建服务器
        try (ServerSocketChannel channel = ServerSocketChannel.open()) {

            //绑定监听窗口
            channel.bind(new InetSocketAddress(8080));
            System.out.println(channel);

            //创建注册，必须为非阻塞
            Selector selector = Selector.open();
            channel.configureBlocking(false);
            //SelectionKey.OP_ACCEPT为绑定事件,绑定的事件 selector 才会关心
            channel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                //方法1、阻塞直到绑定事件发生
                int count = selector.select();

                //方法2、阻塞直到绑定事件发生，或是超时（时间单位为 ms）
                //int count = selector.select(long timeout);

                //方法3、不会阻塞，也就是不管有没有事件，立刻返回，自己根据返回值检查是否有事件
                //int count = selector.selectNow();

                log.debug("select count: {}", count);

                // 获取所有事件
                Set<SelectionKey> keys = selector.selectedKeys();

                // 遍历所有事件，逐一处理
                Iterator<SelectionKey> iter = keys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    // 判断事件类型 accept类型
                    if (key.isAcceptable()) {
                        ServerSocketChannel c = (ServerSocketChannel) key.channel();
                        // 必须处理
                        SocketChannel sc = c.accept();
                        log.debug("{}", sc);
                    }
                    // 处理完毕，必须将事件移除
                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
