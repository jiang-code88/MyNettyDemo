package com.nettyhome._4_wxIM.attribute;

import com.nettyhome._4_wxIM.session.Session;
import io.netty.util.AttributeKey;

/**
 * 连接channel中的绑定属性
 */
public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

}
