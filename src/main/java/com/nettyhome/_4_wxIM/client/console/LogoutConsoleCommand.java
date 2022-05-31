package com.nettyhome._4_wxIM.client.console;

import com.nettyhome._4_wxIM.protocol.request.LogoutRequestPacket;
import com.nettyhome._4_wxIM.session.Session;
import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jrk
 * @date 2022-05-31 11:42.
 */
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        Session session = SessionUtil.getSession(channel);
        logoutRequestPacket.setUserId(session.getUserId());
        logoutRequestPacket.setUsername(session.getUsername());
        channel.writeAndFlush(logoutRequestPacket);
    }
}
