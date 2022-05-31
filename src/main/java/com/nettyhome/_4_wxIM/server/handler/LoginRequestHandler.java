package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.request.LoginRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.LoginResponsePacket;
import com.nettyhome._4_wxIM.session.Session;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * @author jrk
 * @date 2022-05-11 22:22.
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录……");
        // 创建登录响应数据包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUsername(loginRequestPacket.getUsername());

        // 校验登录状态
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            System.out.println(new Date() + ": [" + loginRequestPacket.getUsername() + "]" + ": 登录成功。"+"userId 为: [" + userId + "]");
            SessionUtil.bindSession(ctx.channel(),new Session(userId,loginRequestPacket.getUsername()));
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }
        // 返回登录响应数据包
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    /**
     * 校验登录是否成功
     * @param loginRequestPacket
     * @return
     */
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    /**
     * 随机生成userId
     * @return
     */
    private String randomUserId(){
        return UUID.randomUUID().toString().split("-")[0];
    }

    /**
     * 当连接channel被close后将回调该方法，将channel的session信息移除
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
