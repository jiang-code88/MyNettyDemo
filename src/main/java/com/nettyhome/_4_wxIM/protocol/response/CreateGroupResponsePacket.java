package com.nettyhome._4_wxIM.protocol.response;

import com.nettyhome._4_wxIM.protocol.Packet;
import com.nettyhome._4_wxIM.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @author jrk
 * @date 2022-05-31 15:12.
 */
@Data
public class CreateGroupResponsePacket extends Packet {
    private String groupId;
    private boolean success;
    private String reason;
    private List<String> GroupUsernameList;
    private List<String> GroupUserIdList;


    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
