package com.nettyhome._4_wxIM.protocol.request;

import com.nettyhome._4_wxIM.coder.PacketCodecHandler;
import com.nettyhome._4_wxIM.protocol.Packet;
import com.nettyhome._4_wxIM.protocol.command.Command;

/**
 * @author jrk
 * @date 2022-06-04 16:36.
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
