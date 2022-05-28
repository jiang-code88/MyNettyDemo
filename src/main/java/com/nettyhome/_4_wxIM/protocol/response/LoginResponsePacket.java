package com.nettyhome._4_wxIM.protocol.response;

import com.nettyhome._4_wxIM.protocol.Packet;
import lombok.Data;

import static com.nettyhome._4_wxIM.protocol.command.Command.LOGIN_RESPONSE;

/**
 * 登录响应数据包
 */
@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String username;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
