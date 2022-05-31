package com.nettyhome._4_wxIM.protocol.request;

import com.nettyhome._4_wxIM.protocol.Packet;
import lombok.Data;

import static com.nettyhome._4_wxIM.protocol.command.Command.LOGIN_REQUEST;

/**
 * 登录请求数据包
 */
@Data
public class LoginRequestPacket extends Packet {
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
