package com.xiaosuokeji.server.tag;

import com.xiaosuokeji.framework.spring.XSSpringContext;
import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.service.intf.security.SecResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * 获取指定资源所需权限标签
 * Created by xuxiaowei on 2017/11/8.
 */
public class PermissionTag extends SimpleTagSupport {

    private static final Logger logger = LoggerFactory.getLogger(PermissionTag.class);

    private static SecResourceService secResourceService = XSSpringContext.getBean("secResourceServiceImpl");

    /**
     * 资源的key
     */
    private String key;

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        try {
            SecResource secResource = secResourceService.getByKey(new SecResource(key));
            StringBuilder permissions = new StringBuilder();
            if (secResource != null) {
                for (SecRole item : secResource.getRoleList()) {
                    permissions.append(item.getName()).append(",");
                }
            }
            if (permissions.length() > 0) {
                permissions.deleteCharAt(permissions.length() - 1);
            }
            out.println(permissions.toString());
        } catch (Exception e) {
            logger.error("error : ", e);
        }
    }
}
