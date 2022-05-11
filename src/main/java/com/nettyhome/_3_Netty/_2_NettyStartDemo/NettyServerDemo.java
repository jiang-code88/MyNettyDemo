package com.nettyhome._3_Netty._2_NettyStartDemo;

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
 * +异步机制实现端口递增绑定
 */
public class NettyServerDemo {
    // 服务端初始绑定的端口
    private static final int BEGIN_PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");

        serverBootstrap
                // group(parentGroup,childGroup)
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // childHandler()-用于指定处理新连接数据的读写处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        //System.out.println(ch.attr(clientKey).get());
                    }
                });

        // option(option,value)-设置服务端(bossGroup)channel属性
        serverBootstrap
                // ChannelOption.SO_BACKLOG 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，
                // 如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024);
        // childAttr(childOption,value)-设置每条连接的TCP底层相关属性
        serverBootstrap
                // ChannelOption.SO_KEEPALIVE 表示是否开启TCP底层心跳机制，true-开启
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // ChannelOption.TCP_NODELAY 表示是否开始 Nagle 算法 true-关闭
                // 如果要求高实时性，有数据发送时就马上发送，就设置为 true 关闭
                // 如果需要减少发送次数减少网络交互，就设置为 false 开启
                .childOption(ChannelOption.TCP_NODELAY, true);

        // attr(key,value)-给NioServerSocketChannel指定一些自定义属性，相当于给NioServerSocketChannel维护一个map
        // 通过channel.attr(key)取出该属性
        serverBootstrap
                .attr(AttributeKey.newInstance("serverName"), "nettyServer");
        // childAttr(childKey,value)-给每一条连接指定自定义属性
        // 通过channel.attr(key)取出该属性
        serverBootstrap
                .childAttr(clientKey, "clientValue");

        // handler()-指定在服务端启动过程中的一些逻辑
        serverBootstrap
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel channel){
                        System.out.println("服务端启动中...");
                    }
                });

        // 服务端从 BEGIN_PORT 端口开始绑定(如果端口绑定失败则+1重试)
        bind(serverBootstrap, BEGIN_PORT);
    }

    // bind 方法是异步的，我们可以通过这个异步机制实现端口递增绑定
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        // 发起绑定后，主线程不会阻塞等待bind操作结束，而是直接返回，主线程继续执行。
        // 我们可以添加一个Listener给bind的异步操作，bind操作结束后将调用Listener中的回调函数(传递回调结果：是否绑定成功)
        // 在 Listener 的回调函数中进行再次绑定操作。
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