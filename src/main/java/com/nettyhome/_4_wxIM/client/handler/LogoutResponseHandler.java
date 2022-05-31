package com.nettyhome._4_wxIM.client.handler;

import com.nettyhome._4_wxIM.protocol.response.LogoutResponsePacket;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jrk
 * @date 2022-05-31 12:09.
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        System.out.println(new Date() + " 用户登出操作完成!");
    }
}
