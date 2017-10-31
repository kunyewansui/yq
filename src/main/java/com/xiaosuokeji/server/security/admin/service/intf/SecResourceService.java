package com.xiaosuokeji.server.security.admin.service.intf;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecRole;

import java.util.List;

/**
 * 系统资源Service
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecResourceService {

    void save(SecResource secResource) throws XSBusinessException;

    void remove(SecResource secResource) throws XSBusinessException;

    void update(SecResource secResource) throws XSBusinessException;

    SecResource get(SecResource secResource) throws XSBusinessException;

    XSPageModel listAndCount(SecResource secResource);

    /**
     * 获取指定请求的角色列表
     * @param secResource 参数url，method
     * @return 角色列表
     */
    List<SecRole> listRoleByRequest(SecResource secResource);
}
