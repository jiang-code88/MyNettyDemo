package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.PacketCodeC;
import com.nettyhome._4_wxIM.protocol.request.MessageRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jrk
 * @date 2022-05-11 22:22.
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        // 创建消息响应数据包
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(new Date() + ": 服务端收到客户端消息: " + messageRequestPacket.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
        // 发送响应包
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
