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
                        // (2)每间隔两秒，写入带有时间戳的字符串数据
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        Thread.sleep(200000);
                        // 限定信息传递次数-2次
                        if(times++ == 1) {
                            // 关闭连接——服务端read()将返回-1
                            //socket.close();
                            // 线程结束——服务端read()将报connection reset错误
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
