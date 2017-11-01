package com.xiaosuokeji.server.security.app.service.intf;

import com.xiaosuokeji.server.model.user.User;

/**
 * 令牌Service
 * Created by xuxiaowei on 2017/10/26.
 */
public interface TokenService {

    /**
     * 清除超过有效期的令牌
     */
    void removeExpired();

    /**
     * 检验令牌
     * @param token 令牌
     * @return 令牌所属的用户
     * @throws Exception 令牌无效
     */
    User verify(String token) throws Exception;
}
