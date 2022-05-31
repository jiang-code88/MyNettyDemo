package com.nettyhome._4_wxIM.client.handler;

import com.nettyhome._4_wxIM.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author jrk
 * @date 2022-05-11 22:21.
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUsername = messageResponsePacket.getFromUsername();
        System.out.println("[" + fromUserId + "]:" +"["+ fromUsername + "] -> " + messageResponsePacket.getMessage());}
}
