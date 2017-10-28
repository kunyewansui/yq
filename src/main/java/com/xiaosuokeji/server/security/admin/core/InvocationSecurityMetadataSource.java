package com.xiaosuokeji.server.security.admin.core;

import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecRole;
import com.xiaosuokeji.server.security.admin.service.intf.SecResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 系统用户请求Service
 * Created by xuxiaowei on 2017/10/27.
 */
@Service("securityMetadataSource")
public class InvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SecResourceService secResourceService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getRequest().getMethod();
        //去除请求中的参数
        int firstQuestionMarkIndex = url.lastIndexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        //去除请求末尾多余的"/"
        int firstEndSlashIndex = -1;
        for (int i=url.length() - 1; i>=0; i--) {
            if (url.charAt(i) == '/') {
                firstEndSlashIndex = i;
            } else {
                break;
            }
        }
        if (firstEndSlashIndex != -1) {
            url = url.substring(0, firstEndSlashIndex);
        }

        Collection<ConfigAttribute> attributes = new ArrayList<>();
        List<SecRole> roleList = secResourceService.listRole(new SecResource(url, method));
        for (SecRole role : roleList) {
            attributes.add(new SecurityConfig("ROLE_" + role.getName()));
        }
        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
