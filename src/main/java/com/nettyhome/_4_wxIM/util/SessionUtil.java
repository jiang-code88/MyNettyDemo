package com.nettyhome._4_wxIM.util;

import com.nettyhome._4_wxIM.attribute.*;
import com.nettyhome._4_wxIM.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.Attribute;

import java.util.HashMap;
import java.util.Map;

/**
 * 对channel设置session状态的工具类
 */
public class SessionUtil {

    private static final Map<String,Channel> userIdChannelMap = new HashMap<String,Channel>();
    private static final Map<String,ChannelGroup> groupIdChannelGroupMap = new HashMap<>();

    /**
     * 连接绑定登录会话信息
     * @param channel
     */
    public static void bindSession(Channel channel, Session session) {
        userIdChannelMap.put(session.getUserId(),channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel){
        if(hasLogin(channel)){
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 判断连接的登录会话标记
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
        //Attribute<Session> loginAttr = channel.attr(Attributes.SESSION);
        //return loginAttr.get() != null;
    }

    /**
     * 根据channel获取session会话信息
     * @param channel
     * @return
     */
    public static Session getSession(Channel channel){
        return channel != null ? channel.attr(Attributes.SESSION).get() : null;
    }

    /**
     * 根据session会话信息中的userId信息获取channel
     * @param userId
     * @return
     */
    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup){
        groupIdChannelGroupMap.put(groupId,channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId){
        return groupIdChannelGroupMap.get(groupId);
    }
}
