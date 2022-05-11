package com.nettyhome._4_wxIM.protocol.response;

import com.nettyhome._4_wxIM.protocol.Packet;
import lombok.Data;

import static com.nettyhome._4_wxIM.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * 消息响应数据包
 */
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
