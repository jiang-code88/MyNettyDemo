package com.nettyhome._4_wxIM.client.console;

import com.nettyhome._4_wxIM.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jrk
 * @date 2022-06-03 13:18.
 */
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        String toGroupId = scanner.next();
        String message = scanner.next();

        GroupMessageRequestPacket groupMessageRequestPacket = new GroupMessageRequestPacket();
        groupMessageRequestPacket.setToGroupId(toGroupId);
        groupMessageRequestPacket.setMessage(message);

        channel.writeAndFlush(groupMessageRequestPacket);
    }
}
