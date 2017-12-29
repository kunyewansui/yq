package com.xiaosuokeji.server.tag;

import com.xiaosuokeji.framework.spring.XSSpringContext;
import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.service.security.SecResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 获取指定资源所需的权限
 * Created by xuxiaowei on 2017/11/8.
 */
public class PermissionTag extends SimpleTagSupport {

    private static final Logger logger = LoggerFactory.getLogger(PermissionTag.class);

    private static SecResourceService secResourceService = XSSpringContext.getBean("secResourceService");

    public static String getPermissions(String resourceKey) {
        try {
            SecResource secResource = secResourceService.getByKey(new SecResource(resourceKey));
            if (secResource != null) {
                return "\"" + secResource.getRolesStr() + "\"";
            }
        } catch (Exception e) {
            logger.error("error : ", e);
        }
        return "\"\"";
    }
}
