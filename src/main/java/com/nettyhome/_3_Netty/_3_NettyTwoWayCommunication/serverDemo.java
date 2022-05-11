package com.nettyhome._3_Netty._3_NettyTwoWayCommunication;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author jrk
 * @date 2022-04-12 16:53.
 */
@Slf4j(topic = "c.serverDemo")
public class serverDemo {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boss, worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel channel) throws Exception {
                channel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf byteBuf = (ByteBuf) msg;
                        String msgStr = byteBuf.toString(StandardCharsets.UTF_8);
                        log.debug("服务端接收->{}",msgStr);
                        log.debug("服务端发送->{}",msgStr);
                        ctx.channel().writeAndFlush(msg);
                    }
                });
            }
        });
        bind(serverBootstrap,8000);
    }
    public static void bind(ServerBootstrap serverBootstrap,int port){
        ChannelFuture future = serverBootstrap.bind(port);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    log.debug("服务端绑定端口{}成功",port);
                }else{
                    log.debug("服务端绑定端口{}失败，重新绑定递增下一个端口号{}",port,port+1);
                    bind(serverBootstrap,port+1);
                }
            }
        });
    }
}
