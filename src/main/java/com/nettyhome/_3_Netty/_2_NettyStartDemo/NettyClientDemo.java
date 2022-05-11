package com.nettyhome._3_Netty._2_NettyStartDemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Netty客户端应用程序启动的标准流程
* +失败重连 + 指数避让重连 的回调机制
 */
public class NettyClientDemo {
    private static int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        /*
        * 客户端的引导类 bootstrap 可以指定很多的连接模式
        * */
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定处理连接的线程模型
                .group(workerGroup)
                // 2.指定连接的 IO 模型类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.指定 IO 中的处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                    }
                });

        // attr(key,value)-给NioSocketChannel绑定自定义属性，相当于给NioSocketChannel维护一个map
        // 使用channel.attr()取出该属性
        bootstrap
                .attr(AttributeKey.newInstance("clientName"), "nettyClient");

        // option(option,value)-配置TCP连接参数
        bootstrap
                // ChannelOption.CONNECT_TIMEOUT_MILLIS 表示连接的超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                // ChannelOption.SO_KEEPALIVE 表示是否开启 TCP 底层心跳机制 true-开启
                .option(ChannelOption.SO_KEEPALIVE, true)
                // ChannelOption.TCP_NODELAY 表示是否开始 Nagle 算法 true-关闭
                // 如果要求高实时性，有数据发送时就马上发送，就设置为 true 关闭
                // 如果需要减少发送次数减少网络交互，就设置为 false 开启
                .option(ChannelOption.TCP_NODELAY, true);

        // connect(host,port)-客户端与服务端建立连接
        /*
            bootstrap.connect("juejin.cn", 80).addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接成功!");
                } else {
                    System.err.println("连接失败!");
                }
            });
        */
        connect(bootstrap, "juejin.cn", 80, MAX_RETRY);
    }

    /**
     * 失败重连 + 指数避让形式的等待一段时间再重连 的回调机制
     * 指数退避重连：每隔 1 秒、2 秒、4 秒、8 秒，以 2 的幂次来建立连接，然后到达一定次数之后就放弃连接。
     * @param bootstrap
     * @param host
     * @param port
     * @param retry 剩余尝试重连次数
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // order-第几次尝试重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order; // 2^1、2^2、2^3....
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap
                    .config()
                    .group()
                    .schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
