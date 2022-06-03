package com.nettyhome._4_wxIM.client;

import com.nettyhome._4_wxIM.client.console.ConsoleCommandManage;
import com.nettyhome._4_wxIM.client.console.LoginConsoleCommand;
import com.nettyhome._4_wxIM.client.console.SendToUserConsoleCommand;
import com.nettyhome._4_wxIM.client.handler.*;
import com.nettyhome._4_wxIM.coder.PacketDecoder;
import com.nettyhome._4_wxIM.coder.PacketEncoder;
import com.nettyhome._4_wxIM.coder.Splitter;
import com.nettyhome._4_wxIM.protocol.PacketCodeC;
import com.nettyhome._4_wxIM.protocol.request.LoginRequestPacket;
import com.nettyhome._4_wxIM.protocol.request.MessageRequestPacket;
import com.nettyhome._4_wxIM.session.Session;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

import javax.sound.midi.Soundbank;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 客户端
 */
public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;


    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        //ch.pipeline().addLast(new ClientHandler());
                        // 基于长度域的拆包器
                        //ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        ch.pipeline().addLast(new Splitter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程...");
                // 连接成功之后，启动控制台线程
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap
                        .config()
                        .group()
                        .schedule(() -> connect(bootstrap, host, port, retry - 1),
                                delay, TimeUnit.SECONDS);
            }
        });
    }

    /**
     * 连接成功后开启控制台监听线程，监听客户端控制台的消息输入并发送
     * @param channel
     */
    /*
        private static void startConsoleThread(Channel channel) {
            new Thread(() -> {
                Scanner sc = new Scanner(System.in);
                while (!Thread.interrupted()) {
                    if (SessionUtil.hasLogin(channel)) {
                        String toUserId = sc.next();
                        String message = sc.next();
                        MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                        messageRequestPacket.setToUserId(toUserId);
                        messageRequestPacket.setMessage(message);
                        channel.writeAndFlush(messageRequestPacket);
                    }else{
                        System.out.println("请输入用户名登录：");
                        String username = sc.nextLine();
                        // 1.创建登录请求数据包
                        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
                        loginRequestPacket.setUsername(username);
                        loginRequestPacket.setPassword("pwd");

                        // 3.发送数据
                        channel.writeAndFlush(loginRequestPacket);
                        waitForLoginResponse();
                    }
                }
            }).start();
        }*/
    /*private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    } */
    private static void startConsoleThread(Channel channel) {
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        ConsoleCommandManage consoleCommandManage = new ConsoleCommandManage();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner,channel);
                }else{
                    consoleCommandManage.exec(scanner,channel);
                }
            }
        }).start();
    }
}
