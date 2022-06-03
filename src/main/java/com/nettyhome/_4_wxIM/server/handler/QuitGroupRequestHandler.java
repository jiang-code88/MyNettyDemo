package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.request.QuitGroupRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.QuitGroupResponsePacket;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author jrk
 * @date 2022-06-02 12:18.
 */
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        if(channelGroup != null){
            channelGroup.remove(ctx.channel());
            quitGroupResponsePacket.setGroupId(groupId);
            quitGroupResponsePacket.setSuccess(true);
        }else{
            quitGroupRequestPacket.setGroupId(groupId);
            quitGroupResponsePacket.setSuccess(false);
        }
        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
