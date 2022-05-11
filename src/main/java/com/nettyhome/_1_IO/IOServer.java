package com.nettyhome._1_IO;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO编程模型——服务端
 * 1.启动线程accept()等待连接
 * 2.获得连接后，启动子线程，循环read()字节流中数据
 * 3.子线程只有当连接异常或关闭时才会停止运行
 */
@Slf4j(topic = "c.IOServer")
public class IOServer {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8000);

        // (1) 接收新连接线程
        new Thread(() -> {
            while (true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    log.debug("serverSocket等待阻塞连接");
                    // accept() 首先判断是否有连接，有连接继续运行，没有连接线程阻塞等待被唤醒。
                    Socket socket = serverSocket.accept();

                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            // (3) 按字节流方式读取数据
                                // read() 当连接的inputStream不可用或没有数据时会read()一直阻塞等待
                                // len == -1 表示字节流中已经没有内容可读-->客户端的socket.close()时会出现-1.
                                // 客户端线程停止运行时，read() 方法会抛出connection reset 错误而停止监听字节流中数据。
                            while ((len = inputStream.read(data)) != -1) {
                                //System.out.println(new String(data, 0, len));
                                log.debug("服务端接收信息长度{}",len);
                                log.debug("服务端接收信息内容{}",new String(data, 0, len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        log.debug("处理该连接的线程关闭");
                    }).start();

                } catch (IOException e) { }

            }
        }).start();
    }
}