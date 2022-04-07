package com.nettyhome.Netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

/**
 * Netty-客户端
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();

        // 客户端，NIO模式的连接读写控制线程——监测写请求连接
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class) // NIO模式的连接
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder()); // 连接中数据的字符串编码器
                    }
                });

        // 客户端连接的IP和端口
        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();

        // 向连接中写入数据操作的内容
        while (true) {
            channel.writeAndFlush(new Date() + ": hello world!");
            // 每间隔两秒，写入带有时间戳的字符串数据
            Thread.sleep(2000);
        }
    }
}