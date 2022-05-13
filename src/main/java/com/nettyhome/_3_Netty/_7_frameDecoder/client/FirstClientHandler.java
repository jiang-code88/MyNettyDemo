package com.nettyhome._3_Netty._7_frameDecoder.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * 客户端的连接的数据读写逻辑
 * Netty 里面数据是以 ByteBuf 为单位的，所有需要写出的数据都必须塞到一个 ByteBuf
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端写出数据");
        // 发送一定数量密集的数据包
        for(int i = 0; i < 30; i++){
            // 数据包的内容
            ctx.channel().writeAndFlush(getByteBuf(ctx,i));
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx,int i) {
        //byte[] bytes = (":你好，欢迎关注我的微信公众号，《闪电侠的博客》").getBytes(Charset.forName("utf-8"));
        //byte[] bytes = (i+":你好，欢迎关注我的微信公众号，《闪电侠的博客》\n").getBytes(Charset.forName("utf-8"));
        byte[] bytes = (i+":你好，欢迎关注我的微信公众号，《闪电侠的博客》").getBytes(Charset.forName("utf-8"));
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
