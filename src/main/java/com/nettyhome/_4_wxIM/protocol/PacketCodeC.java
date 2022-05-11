package com.nettyhome._4_wxIM.protocol;

import com.nettyhome._4_wxIM.protocol.command.Command;
import com.nettyhome._4_wxIM.protocol.request.LoginRequestPacket;
import com.nettyhome._4_wxIM.protocol.request.MessageRequestPacket;
import com.nettyhome._4_wxIM.protocol.response.LoginResponsePacket;
import com.nettyhome._4_wxIM.protocol.response.MessageResponsePacket;
import com.nettyhome._4_wxIM.serialize.Serializer;
import com.nettyhome._4_wxIM.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;


import java.util.HashMap;
import java.util.Map;


/**
 * 数据包的协议编解码器
 */
public class PacketCodeC {

    /**
     * 协议魔数
     */
    private static final int MAGIC_NUMBER = 0x12345678;

    /**
     * 协议编解码器的单例对象
     */
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    /**
     * 数据包标识类型对应表
     */
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    /**
     * 序列化标识类型对应表
     */
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }


    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
