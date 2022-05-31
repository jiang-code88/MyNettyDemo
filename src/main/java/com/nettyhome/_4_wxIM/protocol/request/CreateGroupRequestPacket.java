package com.nettyhome._4_wxIM.protocol.request;

import com.nettyhome._4_wxIM.protocol.Packet;
import com.nettyhome._4_wxIM.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @author jrk
 * @date 2022-05-31 15:03.
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    List<String> groupUserIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
