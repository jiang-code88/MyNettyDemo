package com.nettyhome._4_wxIM.attribute;

import io.netty.util.AttributeKey;

/**
 * 连接channel中的绑定属性
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
