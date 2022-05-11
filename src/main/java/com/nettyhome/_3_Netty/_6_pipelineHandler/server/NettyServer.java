package com.nettyhome._3_Netty._6_pipelineHandler.server;

import com.nettyhome._3_Netty._6_pipelineHandler.server.handler.inbound.InBoundHandlerA;
import com.nettyhome._3_Netty._6_pipelineHandler.server.handler.inbound.InBoundHandlerB;
import com.nettyhome._3_Netty._6_pipelineHandler.server.handler.inbound.InBoundHandlerC;
import com.nettyhome._3_Netty._6_pipelineHandler.server.handler.outbound.OutBoundHandlerA;
import com.nettyhome._3_Netty._6_pipelineHandler.server.handler.outbound.OutBoundHandlerB;
import com.nettyhome._3_Netty._6_pipelineHandler.server.handler.outbound.OutBoundHandlerC;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author jrk
 * @date 2022-05-11 17:25.
 */
public class NettyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        serverBootstrap
                .group(parentGroup,childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new InBoundHandlerA());
                        channel.pipeline().addLast(new InBoundHandlerB());
                        channel.pipeline().addLast(new InBoundHandlerC()); // LastHandler写出数据

                        channel.pipeline().addLast(new OutBoundHandlerA());
                        channel.pipeline().addLast(new OutBoundHandlerB());
                        channel.pipeline().addLast(new OutBoundHandlerC());
                    }
                });
        bind(serverBootstrap,PORT);
    }
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
