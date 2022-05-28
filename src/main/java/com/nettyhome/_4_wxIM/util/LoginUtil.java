package com.nettyhome._4_wxIM.util;

import com.nettyhome._4_wxIM.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author jrk
 * @date 2022-05-28 18:17.
 */
public class LoginUtil {
    /**
     * 连接添加登录成功标记
     * @param channel
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 判断连接的登录成功标记
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }


}
