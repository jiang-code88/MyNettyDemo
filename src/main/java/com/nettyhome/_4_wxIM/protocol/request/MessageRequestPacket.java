package com.nettyhome._4_wxIM.protocol.request;

import com.nettyhome._4_wxIM.protocol.Packet;
import lombok.Data;
import static com.nettyhome._4_wxIM.protocol.command.Command.MESSAGE_REQUEST;

/**
 * 消息请求数据包
 */
@Data
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket() { }

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
