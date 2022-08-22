package com.jtc.nettytest;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j(topic = "c.TestByteBuffer")
public class TestByteBuffer {
    public static void main(String[] args) {
        //FileChannel
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()){
            //缓冲区10字节
            ByteBuffer buffer = ByteBuffer.allocate(10);

            while(true){
                //从channel读取数据，向buffer写入
                int len = channel.read(buffer);
                log.debug("读取到的字节数{}" ,len);
                //如果没有内容
                if(len == -1){
                    break;
                }

                //打印读取到的内容
                buffer.flip();//切换到读模式
                while(buffer.hasRemaining()){
                    byte b = buffer.get();
                    log.debug("实际字节{}",(char)b);
                }
                buffer.clear();//切换到写模式
            }

        } catch (IOException e) {
            //e.printStackTrace();
        }

    }
}
