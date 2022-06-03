package com.nettyhome._4_wxIM.protocol.response;

import com.nettyhome._4_wxIM.protocol.Packet;
import com.nettyhome._4_wxIM.protocol.command.Command;
import com.nettyhome._4_wxIM.session.Session;
import lombok.Data;

/**
 * @author jrk
 * @date 2022-06-03 13:47.
 */
@Data
public class GroupMessageResponsePacket extends Packet {
    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
