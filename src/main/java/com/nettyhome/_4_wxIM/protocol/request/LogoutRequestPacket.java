package com.nettyhome._4_wxIM.protocol.request;

import com.nettyhome._4_wxIM.protocol.Packet;
import com.nettyhome._4_wxIM.protocol.command.Command;
import lombok.Data;

/**
 * @author jrk
 * @date 2022-05-31 11:45.
 */
@Data
public class LogoutRequestPacket extends Packet {
    private String username;
    private String userId;

    public LogoutRequestPacket() { }

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
