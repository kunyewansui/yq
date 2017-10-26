package com.xiaosuokeji.server.model.base;

import com.xiaosuokeji.framework.model.XSGenericModel;
import com.xiaosuokeji.server.model.user.User;

/**
 * 基本模型
 * Created by xuxiaowei on 2017/10/23.
 */
public class BaseModel extends XSGenericModel {

    /**
     * 令牌
     */
    protected String token;

    /**
     * 用户
     */
    protected User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
