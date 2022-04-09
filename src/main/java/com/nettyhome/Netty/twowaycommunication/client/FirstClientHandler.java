package com.nettyhome.Netty.twowaycommunication.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 客户端的数据读写逻辑定义
 * Netty 里面数据是以 ByteBuf 为单位的， 所有需要写出的数据都必须塞到一个 ByteBuf
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端写出数据");

        // 1.获取需要发送的数据——以 netty 对二进制数据的抽象 ByteBuf 存放数据
        ByteBuf buffer = getByteBuf(ctx);

        // 2.把数据写到服务端
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，闪电侠!".getBytes(Charset.forName("utf-8"));

        // ctx.alloc() 是获取到一个 ByteBuf 的内存管理器
        ByteBuf buffer = ctx.alloc().buffer();

        // 把字符串的二进制数据填充到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }

    /**
     * 客户端接收服务端回复的数据
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }
}
