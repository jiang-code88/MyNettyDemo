package com.nettyhome._3_Netty._3_NettyTwoWayCommunication;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author jrk
 * @date 2022-04-12 16:53.
 */
@Slf4j(topic = "c.clientDemo")
public class clientDemo {
    public static int MAX_RETRY = 5;
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                String msg = "hello server";
                                log.debug("客户端发送->{}",msg);
                                ByteBuf byteBuf = ctx.alloc().buffer().writeBytes(msg.getBytes());
                                ctx.channel().writeAndFlush(byteBuf);
                            }
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug("客户端接收->{}",byteBuf.toString(StandardCharsets.UTF_8));
                            }
                        });
                    }
                });

        connect(bootstrap,"127.0.0.1",8000,MAX_RETRY);
    }

    public static void connect(Bootstrap bootstrap, String IPAddress, int port, int retryTimes){
        ChannelFuture connect = bootstrap.connect(IPAddress, port);
        connect.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    log.debug("客户端连接成功iP:{}，port{}",IPAddress,port);
                }else if(retryTimes == 0){
                    log.debug("客户端重连次数已经到达最大次数!");
                }else{
                    int order = MAX_RETRY - retryTimes;
                    int delay = 1 << order;
                    log.debug("客户端连接失败,正在进行{}次重新连接...ip{},port{}",order,IPAddress,port);
                    bootstrap
                            .config()
                            .group()
                            .schedule(()->connect(bootstrap,IPAddress,port,retryTimes-1),
                                    delay, TimeUnit.SECONDS);
                }
            }
        });
    }
}
