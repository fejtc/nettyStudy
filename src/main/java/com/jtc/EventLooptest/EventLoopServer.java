package com.jtc.EventLooptest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override     //连接建立后调用该方法initChannel
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //连接建立后添加处理器
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override     //关注读事件
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                //super.channelRead(ctx, msg);
                                ByteBuf buf = (ByteBuf) msg;
                                //转为字符串打印，指定字符集
                                log.debug(buf.toString(Charset.defaultCharset()));

                            }
                        });
                    }
                })
                .bind(8080);
    }
}
