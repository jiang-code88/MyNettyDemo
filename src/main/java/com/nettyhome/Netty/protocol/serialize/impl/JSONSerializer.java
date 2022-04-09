package com.nettyhome.Netty.protocol.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.nettyhome.Netty.protocol.serialize.Serializer;
import com.nettyhome.Netty.protocol.serialize.SerializerAlogrithm;

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
