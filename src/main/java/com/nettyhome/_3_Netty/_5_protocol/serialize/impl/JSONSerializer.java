package com.nettyhome._3_Netty._5_protocol.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.nettyhome._3_Netty._5_protocol.serialize.Serializer;
import com.nettyhome._3_Netty._5_protocol.serialize.SerializerAlogrithm;

/**
 * fastjson序列化算法
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
