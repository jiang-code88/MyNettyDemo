package com.nettyhome._3_Netty._5_protocol;

import com.nettyhome._3_Netty._5_protocol.command.LoginRequestPacket;
import com.nettyhome._3_Netty._5_protocol.command.Packet;
import com.nettyhome._3_Netty._5_protocol.serialize.Serializer;
import com.nettyhome._3_Netty._5_protocol.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

/**
 * 协议数据包编解码器-测试方式
 */
public class PacketCodeCTest {
    @Test
    public void encodeTest() {

        Serializer serializer = new JSONSerializer();

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("username");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}
