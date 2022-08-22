package com.jtc.nettytest;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static com.jtc.nettytest.ByteBufferUtil.debugAll;

public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);
        //debugAll(buffer);

        buffer.put(new byte[]{0x65,0x66,0x64});
        //debugAll(buffer);


        //堆内存   与   直接内存
        System.out.println(ByteBuffer.allocate(15).getClass());
        System.out.println(ByteBuffer.allocateDirect(15).getClass());



        System.out.println(Charset.defaultCharset().decode(buffer));

    }
}
