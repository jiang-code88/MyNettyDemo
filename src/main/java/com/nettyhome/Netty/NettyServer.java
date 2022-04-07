package com.nettyhome.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty-服务器
 */
@Slf4j(topic = "c.NettyServer")
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 服务端，NIO模式的连接控制线程——接收服务端所有的连接，并绑定到selector上
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 服务端，NIO模式的连接读写控制线程——selector线程，负责轮询其上所有的连接，监测读请求连接
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class) // NIO模式的连接
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder()); // 连接中数据的字符串解码器
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                //System.out.println(msg); // 读取连接中数据后的操作
                                log.debug("服务端接收到的消息:{}",msg);
                            }
                        });
                    }
                })
                .bind(8000); // 服务器监听的端口
    }
}

