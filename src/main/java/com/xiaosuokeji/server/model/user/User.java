package com.xiaosuokeji.server.model.user;

import com.xiaosuokeji.framework.model.XSGenericModel;

/**
 * 用户
 * Created by xuxiaowei on 2017/10/26.
 */
public class User extends XSGenericModel {

    /**
     * id
     */
    private String id;

    public User() {}

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
