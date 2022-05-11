package com.nettyhome._3_Netty._1_NettyHelloWorld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Netty-服务器
 */
@Slf4j(topic = "c.NettyServer")
public class NettyServer {
    public static void main(String[] args) {
        // 引导类——进行服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 服务端，NIO模式的连接控制线程组——accept接收服务端所有新连接，并绑定到selector上。
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 服务端，NIO模式的连接的数据读写控制线程组——selector线程，负责轮询其上所有的连接，监测连接的读/写请求
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class) // NIO模型的连接
                // ChannelInitializer 用于定义后续每条连接的数据读写，业务处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        /*ch.pipeline().addLast(new StringDecoder()); // 连接中数据的字符串解码器
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                //System.out.println(msg); // 读取连接中数据后的操作
                                log.debug("服务端接收到的消息:{}",msg);
                            }
                        });*/
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
                            }
                        });
                    }
                })
                .bind(8000); // 服务器监听的端口
    }
}

