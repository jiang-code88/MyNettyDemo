package com.nettyhome._4_wxIM.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.nettyhome._4_wxIM.serialize.Serializer;
import com.nettyhome._4_wxIM.serialize.SerializerAlogrithm;


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
