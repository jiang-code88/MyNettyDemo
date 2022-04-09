package com.nettyhome.Netty.StartDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Netty服务端应用程序启动的标准流程
 */
public class NettyServerDemo {
    // 服务端初始绑定的端口
    private static final int BEGIN_PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // attr()方法用于给 NioServerSocketChannel 指定一些自定义属性，给 NioServerSocketChannel维护一个map
                // 通过 channel.attr() 可以取出这个属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                // childAttr 可以给每一条连接指定自定义属性，然后后续我们可以通过channel.attr()取出该属性
                .childAttr(clientKey, "clientValue")
                .option(ChannelOption.SO_BACKLOG, 1024)
                // childOption()可以给每条连接设置一些TCP底层相关的属性参数
                .childOption(ChannelOption.SO_KEEPALIVE, true)// 开启TCP底层心跳机制，true为开启
                /*
                * 要求高实时性，有数据发送时就马上发送，就关闭
                * 如果需要减少发送次数减少网络交互，就开启
                * */
                .childOption(ChannelOption.TCP_NODELAY, true) // 开启Nagle算法，true表示关闭，false表示开启
                // childHandler()用于指定处理新连接数据的读写处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        System.out.println(ch.attr(clientKey).get());
                    }
                });

        // 服务端从BEGIN_PORT端口开始绑定(端口绑定失败+1重试)
        bind(serverBootstrap, BEGIN_PORT);
    }

    // bind 方法是异步的，我们可以通过这个异步机制实现端口递增绑定
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        // 发起绑定后，主线程不会阻塞等待bind操作结束，而是直接返回，主线程继续执行。
        // 我们可以添加一个Listener给bind的异步操作，bind操作结束后将调用Listener中的回调函数(传递回调结果：是否绑定成功)
        // Listener 的回调函数中可以再次进行绑定操作。
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1); // 递归调用，循环绑定
                }
            }
        });
    }
}