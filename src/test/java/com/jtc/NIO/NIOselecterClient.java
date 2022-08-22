package com.jtc.NIO;

import java.io.IOException;
import java.net.Socket;

public class NIOselecterClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080)) {
            System.out.println(socket);
            socket.getOutputStream().write("world".getBytes());
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
