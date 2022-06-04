package com.nettyhome._4_wxIM.server.handler;

import com.nettyhome._4_wxIM.protocol.Packet;
import com.nettyhome._4_wxIM.protocol.command.Command;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jrk
 * @date 2022-06-03 17:12.
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();
            handlerMap.put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
            handlerMap.put(Command.LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
            handlerMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
            handlerMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
            handlerMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
            handlerMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
            handlerMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);

            /*handlerMap.put(Command.MESSAGE_RESPONSE,.INSTANCE);
            handlerMap.put(Command.LOGOUT_RESPONSE,.INSTANCE);
            handlerMap.put(Command.CREATE_GROUP_RESPONSE,.INSTANCE);
            handlerMap.put(Command.JOIN_GROUP_RESPONSE,.INSTANCE);
            handlerMap.put(Command.QUIT_GROUP_RESPONSE,.INSTANCE);
            handlerMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE,.INSTANCE);
            handlerMap.put(Command.GROUP_MESSAGE_RESPONSE,.INSTANCE);*/
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx,packet);
    }
}
