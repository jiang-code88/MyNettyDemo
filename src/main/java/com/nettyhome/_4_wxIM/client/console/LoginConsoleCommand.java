package com.nettyhome._4_wxIM.client.console;

import com.nettyhome._4_wxIM.protocol.request.LoginRequestPacket;
import com.nettyhome._4_wxIM.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jrk
 * @date 2022-05-31 11:32.
 */
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入用户名登录：");
        String username = scanner.nextLine();
        // 1.创建登录请求数据包
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUsername(username);
        loginRequestPacket.setPassword("pwd");

        // 3.发送数据
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }
    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
