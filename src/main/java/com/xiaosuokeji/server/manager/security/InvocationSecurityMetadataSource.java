package com.xiaosuokeji.server.manager.security;

import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.service.intf.security.SecResourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 系统用户请求拦截Service
 * Created by xuxiaowei on 2017/10/27.
 */
@Service("securityMetadataSource")
public class InvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger logger = LoggerFactory.getLogger(InvocationSecurityMetadataSource.class);

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
        for (int i = url.length() - 1; i >= 0; --i) {
            if (url.charAt(i) == '/') {
                firstEndSlashIndex = i;
            }
            else {
                break;
            }
        }
        if (firstEndSlashIndex != -1) {
            url = url.substring(0, firstEndSlashIndex);
        }
        Collection<ConfigAttribute> attributes = new ArrayList<>();
        try {
            SecResource secResource = secResourceService.getByRequest(new SecResource(url, method));
            if (secResource != null) {
                List<SecRole> roleList = secResource.getRoleList();
                for (SecRole role : roleList) {
                    attributes.add(new SecurityConfig("ROLE_" + role.getName()));
                }
            }
        } catch (Exception e) {
            logger.error("error : ", e);
        }
        if (attributes.size() == 0) {
            logger.debug("请求{}没有被SpringSecurity控制起来，如需权限控制请在管理后台添加相应的系统资源", url + "(" + method + ")");
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
