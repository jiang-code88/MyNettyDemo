package com.nettyhome._4_wxIM.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author jrk
 * @date 2022-05-31 11:29.
 */
public interface ConsoleCommand {
    public void exec(Scanner scanner, Channel channel);
}
