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

    List<SecResource> tree(SecResource secResource);

    List<SecRole> listRole(SecResource secResource);
}
