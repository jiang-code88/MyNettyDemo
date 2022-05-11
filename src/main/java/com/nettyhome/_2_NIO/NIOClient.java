package com.nettyhome._2_NIO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * NIO编程模型——客户端
 */
@Slf4j(topic = "c.NIOClient")
public class NIOClient {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                // (1)建立一个socket连接
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(200000);
                    } catch (Exception e) { }
                }
            } catch (IOException e) {
            }
        }).start();
    }

}
