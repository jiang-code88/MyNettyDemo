package com.nettyhome._4_wxIM.protocol.response;

import com.nettyhome._4_wxIM.protocol.Packet;
import com.nettyhome._4_wxIM.protocol.command.Command;
import lombok.Data;

/**
 * @author jrk
 * @date 2022-05-31 12:00.
 */
@Data
public class LogoutResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
