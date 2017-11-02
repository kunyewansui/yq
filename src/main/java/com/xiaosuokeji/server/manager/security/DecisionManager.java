package com.xiaosuokeji.server.manager.security;

import com.xiaosuokeji.server.constant.security.SecStaffConsts;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 系统用户请求权限校验Service
 * Created by xuxiaowei on 2017/10/27.
 */
@Service("accessDecisionManager")
public class DecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> attributs)
            throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute item : attributs) {
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (item.getAttribute().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(SecStaffConsts.SEC_STAFF_ACCESS_DENY.getMsg());
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
