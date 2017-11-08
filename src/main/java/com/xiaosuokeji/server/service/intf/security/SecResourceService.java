package com.xiaosuokeji.server.service.intf.security;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.security.SecResource;

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

    /**
     * 获取指定的资源及其角色列表
     * @param secResource 参数url，method
     * @return 资源
     */
    SecResource getByRequest(SecResource secResource) throws Exception;

    /**
     * 获取指定的资源及其角色列表
     * @param secResource 参数key
     * @return 资源
     */
    SecResource getByKey(SecResource secResource) throws Exception;

    XSPageModel listAndCount(SecResource secResource);

    List<SecResource> tree(SecResource secResource);

    void invalidateCache();
}
