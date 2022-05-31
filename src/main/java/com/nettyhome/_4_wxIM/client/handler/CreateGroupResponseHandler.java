package com.nettyhome._4_wxIM.client.handler;

import com.nettyhome._4_wxIM.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @author jrk
 * @date 2022-05-31 15:18.
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.print("群里面有：" + createGroupResponsePacket.getGroupUsernameList());
        System.out.println("对应的userId列表为：" + createGroupResponsePacket.getGroupUserIdList());
    }
}
