package com.xiaosuokeji.server.aspect;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.server.annotation.UserAuth;
import com.xiaosuokeji.server.constant.user.TokenConsts;
import com.xiaosuokeji.server.model.base.BaseModel;
import com.xiaosuokeji.server.model.user.User;
import com.xiaosuokeji.server.service.user.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * 令牌校验切面
 * Created by xuxiaowei on 2017/10/26.
 */
@Service
@Aspect
public class UserAuthAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthAspect.class);

    @Autowired
    private TokenService tokenService;

    @Before("@annotation(userAuth)")
    public void doBefore(JoinPoint joinPoint, UserAuth userAuth) throws Throwable {
        Object[] args = joinPoint.getArgs();
        int i = 0;
        for (; i < args.length; i++) {
            if (args[i] instanceof BaseModel) {
                BaseModel baseModel = (BaseModel) args[i];
                try {
                    User user = tokenService.verify(baseModel.getToken());
                    baseModel.setUser(user);
                } catch (Throwable t) {
                    if (userAuth.required()) {
                        if (t instanceof ExecutionException) {
                            t = t.getCause();
                        }
                        throw t;
                    }
                }
                break;
            }
        }
        if (i == args.length) {
            if (userAuth.required()) {
                throw new XSBusinessException(TokenConsts.TOKEN_INVALID);
            }
        }
    }
}
