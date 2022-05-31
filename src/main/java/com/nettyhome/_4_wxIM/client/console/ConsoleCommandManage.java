package com.nettyhome._4_wxIM.client.console;

import com.nettyhome._4_wxIM.util.SessionUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author jrk
 * @date 2022-05-31 12:17.
 */
public class ConsoleCommandManage implements ConsoleCommand {
    Map<String,ConsoleCommand> consoleCommandMap ;

    public ConsoleCommandManage() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command = scanner.next();

        // 检验连接是否处于登录状态
        if (!SessionUtil.hasLogin(channel)) {
            return;
        }

        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if(consoleCommand != null){
            scanner.nextLine(); // 把createGroup指令后的回车给读了
            consoleCommand.exec(scanner,channel);
        }else{
            scanner.nextLine(); // 将错误指令的整行内容忽略读入
            System.err.println("无法识别[" + command + "]指令，请重新输入!");
        }
    }
}
