package com.nettyhome._4_wxIM.protocol.request;

import com.nettyhome._4_wxIM.protocol.Packet;
import com.nettyhome._4_wxIM.protocol.command.Command;
import lombok.Data;

/**
 * @author jrk
 * @date 2022-06-03 13:47.
 */
@Data
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
