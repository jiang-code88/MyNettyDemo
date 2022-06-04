package com.nettyhome._4_wxIM.client.handler;

import com.nettyhome._4_wxIM.protocol.PacketCodeC;
import com.nettyhome._4_wxIM.protocol.request.LoginRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.LoginResponsePacket;
import com.nettyhome._4_wxIM.session.Session;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * @author jrk
 * @date 2022-05-11 22:21.
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 1.创建登录请求数据包
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");

        // 3.发送数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String username = loginResponsePacket.getUsername();

        // 校验登录响应数据包
        if (loginResponsePacket.isSuccess()) {
            System.out.println("[" + username + "]登录成功。userId 为: [" + loginResponsePacket.getUserId() + "]");
            SessionUtil.bindSession(ctx.channel(),new Session(userId,username));
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    // 客户端当前的 channel 被 close() 时触发的回调函数
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "LoginResponseHandler检测到客户端连接被关闭！");
    }
}
