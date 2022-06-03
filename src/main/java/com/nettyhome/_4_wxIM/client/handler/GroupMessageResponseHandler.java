package com.nettyhome._4_wxIM.client.handler;

import com.nettyhome._4_wxIM.protocol.response.GroupMessageResponsePacket;
import com.nettyhome._4_wxIM.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jrk
 * @date 2022-06-03 13:53.
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {
        String fromGroupId = groupMessageResponsePacket.getFromGroupId();
        Session fromUser = groupMessageResponsePacket.getFromUser();
        System.out.println("收到群聊[" + fromGroupId + "]中[" + fromUser + "]发来的消息："
                + groupMessageResponsePacket.getMessage());
    }
}
