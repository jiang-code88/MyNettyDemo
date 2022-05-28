package com.nettyhome._4_wxIM.session;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Predicate;

/**
 * @author jrk
 * @date 2022-05-21 12:07.
 * 登录成功的会话信息类
 */
@Data
public class Session {
    private String userId;
    private String username;

    public Session(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
