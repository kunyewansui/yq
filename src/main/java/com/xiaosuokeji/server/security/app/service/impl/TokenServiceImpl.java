package com.xiaosuokeji.server.security.app.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.util.XSTimeUtil;
import com.xiaosuokeji.server.model.user.User;
import com.xiaosuokeji.server.security.app.constant.TokenConsts;
import com.xiaosuokeji.server.security.app.dao.TokenDao;
import com.xiaosuokeji.server.security.app.model.Token;
import com.xiaosuokeji.server.security.app.service.intf.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 令牌ServiceImpl
 * Created by xuxiaowei on 2017/10/26.
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private TokenDao tokenDao;

    private Cache<String, Token> cache = null;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder().expireAfterAccess(1800L, TimeUnit.SECONDS).maximumSize(4096).build();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeExpired() {
        Long st = System.currentTimeMillis();
        logger.info("执行定时任务：清除超过有效期的令牌...");
        try {
            tokenDao.removeExpired(30, new Date());
            logger.info("执行定时任务：清除超过有效期的令牌，成功！执行时间：" + String.valueOf(System.currentTimeMillis() - st) + "毫秒");
        } catch (Exception e) {
            logger.error("执行定时任务：清除超过有效期的令牌，失败！原因：", e);
        }
    }

    @Override
    public User verify(String tokenStr) throws Exception {
        if (StringUtils.isBlank(tokenStr)) {
            throw new XSBusinessException(TokenConsts.TOKEN_INVALID);
        }
        //先查询缓存，若未命中则查询数据库
        Token token = cache.get(tokenStr, new Callable<Token>() {
            @Override
            public Token call() throws Exception {
                Token token = tokenDao.get(new Token(tokenStr));
                if (token == null) {
                    throw new XSBusinessException(TokenConsts.TOKEN_INVALID);
                }
                return token;
            }
        });
        //校验令牌是否超过30天有效期
        boolean expired = XSTimeUtil.restLifeTime(token.getCreateTime(), 30 * 24 * 60 * 60) <= 0;
        if (expired) {
            tokenDao.remove(token);
            cache.invalidate(token.getId());
            throw new XSBusinessException(TokenConsts.TOKEN_INVALID);
        }
        return token.getUser();
    }
}
