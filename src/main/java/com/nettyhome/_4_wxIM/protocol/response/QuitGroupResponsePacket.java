package com.nettyhome._4_wxIM.protocol.response;

import com.nettyhome._4_wxIM.protocol.Packet;
import com.nettyhome._4_wxIM.protocol.command.Command;
import lombok.Data;

/**
 * @author jrk
 * @date 2022-06-02 11:56.
 */
@Data
public class QuitGroupResponsePacket extends Packet {
    private String groupId;
    private Boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
