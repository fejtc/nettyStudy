package com.jtc.testNetty;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class test01 {
    public static void main(String[] args) {
        //事件循环组
        NioEventLoopGroup group = new NioEventLoopGroup(2);

        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        //定时执行任务
        group.next().scheduleAtFixedRate(() -> {
            log.debug("ok");
        },0,1, TimeUnit.SECONDS);

        log.debug("main");
    }
}
