package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.request.GroupMessageRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.GroupMessageResponsePacket;
import com.nettyhome._4_wxIM.session.Session;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author jrk
 * @date 2022-06-03 13:53.
 */
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        String toGroupId = groupMessageRequestPacket.getToGroupId();

        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(toGroupId);
        groupMessageResponsePacket.setMessage(groupMessageRequestPacket.getMessage());
        groupMessageResponsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(toGroupId);
        channelGroup.forEach((t)->{
            if(t != ctx.channel()){
                t.writeAndFlush(groupMessageResponsePacket);
            }
        });
        //channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
