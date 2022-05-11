package com.nettyhome._3_Netty._5_protocol.command;

import lombok.Data;
import static com.nettyhome._3_Netty._5_protocol.command.Command.LOGIN_REQUEST;

/**
 * 登录指令数据包
 */
@Data
public class LoginRequestPacket extends Packet {
    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
