package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.PacketCodeC;
import com.nettyhome._4_wxIM.protocol.request.MessageRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.MessageResponsePacket;
import com.nettyhome._4_wxIM.session.Session;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jrk
 * @date 2022-05-11 22:22.
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler() { }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 1.获取消息发送方的session信息
        Session fromSession = SessionUtil.getSession(ctx.channel());

        // 2.获取消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if(toUserChannel != null){
            Session toSession = SessionUtil.getSession(toUserChannel);
            // 3.创建消息响应数据包
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            System.out.println(new Date() + ": 服务端收到" +
                    "["+fromSession.getUsername()+"]客户端发送给["+toSession.getUsername()+"]的消息: "
                    + messageRequestPacket.getMessage());
            messageResponsePacket.setFromUserId(fromSession.getUserId());
            messageResponsePacket.setFromUsername(fromSession.getUsername());
            messageResponsePacket.setMessage(messageRequestPacket.getMessage());
            // 4.向接收信息的客户端channel发送响应包
            if(SessionUtil.hasLogin(toUserChannel)){
                System.out.println();
                toUserChannel.writeAndFlush(messageResponsePacket);
            }else{
                System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
            }
        }else{
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }
    }
}
