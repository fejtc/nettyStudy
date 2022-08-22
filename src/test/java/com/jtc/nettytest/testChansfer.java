package com.jtc.nettytest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class testChansfer {
    public static void main(String[] args) throws IOException {
        method0();
        method1();
        method2();
    }

    private static void method0(){
        String FROM = "data.txt";
        String TO = "to.txt";
        long start = System.nanoTime();
        try (
                FileChannel from = new FileInputStream(FROM).getChannel();
                FileChannel to = new FileOutputStream(TO).getChannel();
        ) {
            //效率高，底层会调用操作系统的零拷贝进行优化
            from.transferTo(0, from.size(), to);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.nanoTime();
        System.out.println("transferTo 用时：" + (end - start) / 1000_000.0);
    }

    //传输大文件
    private static void method1(){
        long start = System.nanoTime();
            try (
                    FileChannel from = new FileInputStream("data.txt").getChannel();
                    FileChannel to = new FileOutputStream("to.txt").getChannel();
            ) {
                // 效率高，底层会利用操作系统的零拷贝进行优化
                long size = from.size();
                // left 变量代表还剩余多少字节
                for (long left = size; left > 0; ) {
                    //System.out.println("position:" + (size - left) + " left:" + left);
                    //返回实际传输的字节数
                    left -= from.transferTo((size - left), left, to);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        long end = System.nanoTime();
        System.out.println("transferTo 用时：" + (end - start) / 1000_000.0);
    }

    //遍历文件夹，访问者模式
    private static void method2() throws IOException {
        Path path = Paths.get("D:\\java\\JDK8");
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();

        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)throws IOException {
                System.out.println(dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(dirCount);
        System.out.println(fileCount);
    }

}
