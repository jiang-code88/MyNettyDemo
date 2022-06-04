package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.request.HeartBeatRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jrk
 * @date 2022-06-04 16:34.
 * 服务端发送心跳包（接收客户端发送到来的并转发）
 */
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket heartBeatRequestPacket) throws Exception {
        System.out.println(new Date()+": "+"服务端接收并转发心跳包");
        HeartBeatResponsePacket heartBeatResponsePacket = new HeartBeatResponsePacket();
        ctx.channel().writeAndFlush(heartBeatResponsePacket);
    }
}
