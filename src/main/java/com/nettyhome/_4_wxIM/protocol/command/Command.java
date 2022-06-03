package com.nettyhome._4_wxIM.protocol.command;

/**
 * 指令数据包列表
 */
public interface Command {

    // 登录请求
    Byte LOGIN_REQUEST = 1;

    // 登录响应
    Byte LOGIN_RESPONSE = 2;

    // 消息请求
    Byte MESSAGE_REQUEST = 3;

    // 消息响应
    Byte MESSAGE_RESPONSE = 4;

    // 登出请求
    Byte LOGOUT_REQUEST = 5;

    // 登出响应
    Byte LOGOUT_RESPONSE = 6;

    // 创建群聊请求
    Byte CREATE_GROUP_REQUEST = 7;

    // 创建群聊响应
    Byte CREATE_GROUP_RESPONSE = 8;

    // 加入群聊
    Byte JOIN_GROUP_REQUEST = 9;

    Byte JOIN_GROUP_RESPONSE = 10;

    // 退出群聊
    Byte QUIT_GROUP_REQUEST = 11;

    Byte QUIT_GROUP_RESPONSE = 12;

    // 获取群聊用户列表
    Byte LIST_GROUP_MEMBERS_REQUEST = 13;

    Byte LIST_GROUP_MEMBERS_RESPONSE = 14;
}
