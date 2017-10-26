package com.xiaosuokeji.server.dao.user;

import com.xiaosuokeji.server.model.user.Token;

import java.util.Date;

/**
 * 令牌Dao
 * Created by xuxiaowei on 2017/10/26.
 */
public interface TokenDao {

    int save(Token token);

    int remove(Token token);

    /**
     * 清除超过有效期（创建时间 + 有效天数 <= 当前时间）的令牌
     * @param day 有效天数
     * @param now 当前时间
     * @return 受影响行数
     */
    int removeExpired(Integer day, Date now);

    Token get(Token token);
}
