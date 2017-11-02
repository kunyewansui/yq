package com.xiaosuokeji.server.model.user;

import com.xiaosuokeji.server.model.base.BaseModel;

/**
 * 令牌
 * Created by xuxiaowei on 2017/10/26.
 */
public class Token extends BaseModel {

    /**
     * id
     */
    private String id;

    public Token() {}

    public Token(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
