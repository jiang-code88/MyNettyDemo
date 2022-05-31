package com.nettyhome._4_wxIM.client.console;

import com.nettyhome._4_wxIM.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author jrk
 * @date 2022-05-31 11:43.
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITTER = " ";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("【拉人群聊】输入 userId 列表，userId 之间使用空格隔开");
        String userIdList = scanner.nextLine();
        List<String> groupUserIdList = Arrays.asList(userIdList.split(USER_ID_SPLITTER));
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        createGroupRequestPacket.setGroupUserIdList(groupUserIdList);
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
