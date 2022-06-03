package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.request.ListGroupMembersRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.ListGroupMembersResponsePacket;
import com.nettyhome._4_wxIM.session.Session;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jrk
 * @date 2022-06-02 12:20.
 */
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {
        String groupId = listGroupMembersRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket();
        listGroupMembersResponsePacket.setGroupId(groupId);

        if(channelGroup != null){
            List<Session> groupSessionList = new ArrayList<>();
            for(Channel channel : channelGroup){
                groupSessionList.add(SessionUtil.getSession(channel));
            }
            listGroupMembersResponsePacket.setGroupSessionList(groupSessionList);
        }

        ctx.channel().writeAndFlush(listGroupMembersResponsePacket);
    }
}
