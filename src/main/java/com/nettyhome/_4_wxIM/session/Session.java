package com.nettyhome._4_wxIM.session;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Predicate;

/**
 * @author jrk
 * @date 2022-05-21 12:07.
 * 登录成功的会话信息类
 * 注意：
 * 1.以后每一个类都要加上getter、setter方法
 * 2.有参构造器+无参构造器一定要同时出现或同时省略
 * 3.toString、equals、hashcode方法选择性重写添加
 */
@Data
public class Session {
    private String userId;
    private String username;

    public Session() { }

    public Session(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    @Override
    public String toString() {
        return userId + ":" + username;
    }
}
