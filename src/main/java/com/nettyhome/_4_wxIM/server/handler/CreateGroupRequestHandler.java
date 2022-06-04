package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.request.CreateGroupRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.CreateGroupResponsePacket;
import com.nettyhome._4_wxIM.session.Session;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author jrk
 * @date 2022-05-31 15:19.
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    private CreateGroupRequestHandler() { }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> groupUserIdList = createGroupRequestPacket.getGroupUserIdList();

        // 1.创建群聊的channel分组
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        List<String> groupUsernameList = new ArrayList<>();

        for(String userId : groupUserIdList){
            Channel channel = SessionUtil.getChannel(userId);
            if(channel != null && SessionUtil.hasLogin(channel)){
                channelGroup.add(channel);
                groupUsernameList.add(SessionUtil.getSession(channel).getUsername());
            }
        }

        // 2.创建群聊创建的响应数据包
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setGroupUsernameList(groupUsernameList);
        createGroupResponsePacket.setGroupUserIdList(groupUserIdList);
        String groupId = randomUserId();
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setSuccess(true);

        // 3.向channel分组中所有的成员发送群聊创建响应
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.print("群里面有：" + createGroupResponsePacket.getGroupUsernameList());
        System.out.println("对应的userId列表为：" + groupUserIdList);

        // 4.绑定channelGroup和groupId
        SessionUtil.bindChannelGroup(groupId,channelGroup);
    }

    private String randomUserId(){
        return UUID.randomUUID().toString().split("-")[0];
    }
}
