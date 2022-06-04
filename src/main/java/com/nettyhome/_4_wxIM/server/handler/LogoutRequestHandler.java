package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.request.LogoutRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.LoginResponsePacket;
import com.nettyhome._4_wxIM.protocol.response.LogoutResponsePacket;
import com.nettyhome._4_wxIM.session.Session;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jrk
 * @date 2022-05-31 11:56.
 */
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() { }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket logoutRequestPacket) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());
        SessionUtil.unBindSession(ctx.channel());

        System.out.println(new Date() + ": 服务端处理客户端["+session.getUsername()+"]连接的登出成功");

        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
