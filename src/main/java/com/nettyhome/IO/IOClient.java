package com.nettyhome.IO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * BIO编程模型——客户端
 * 1.启动线程建立连接
 * 2.循环间隔2s向字节流中发送字符串
 */
@Slf4j(topic = "c.IOClient")
public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                // (1)建立一个socket连接
                Socket socket = new Socket("127.0.0.1", 8000);
                int times = 0;

                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(200000);
                        if(times++ == 1) { //第三次传送数据
                            // 关闭连接
                            //socket.close();
                            // 线程结束
                            //break;
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
