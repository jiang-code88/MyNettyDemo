package com.nettyhome._4_wxIM.client.handler;

import com.nettyhome._4_wxIM.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @author jrk
 * @date 2022-06-04 16:30.
 * 客户端发送心跳包——间隔 5s 发送心跳包
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    // 心跳包发送间隔时间
    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeatPacket(ctx);
        super.channelActive(ctx);
    }

    /**
     * 递归调用栈的方式进行channel中间隔发送心跳包
     * @param ctx
     */
    public void scheduleSendHeartBeatPacket(ChannelHandlerContext ctx){
        ctx.executor().schedule(()->{
            if(ctx.channel().isActive()){
                ctx.channel().writeAndFlush(new HeartBeatRequestPacket());
                scheduleSendHeartBeatPacket(ctx);
            }
        },HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}

