package com.nettyhome._4_wxIM.client.console;

import com.nettyhome._4_wxIM.protocol.request.JoinGroupRequestPacket;
import com.nettyhome._4_wxIM.server.handler.JoinGroupRequestHandler;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jrk
 * @date 2022-06-02 11:40.
 */
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("请输入您要加入的群聊Id: ");
        String groupId = scanner.next();

        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
