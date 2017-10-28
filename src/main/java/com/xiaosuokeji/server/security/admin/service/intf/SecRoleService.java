package com.xiaosuokeji.server.security.admin.service.intf;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecRole;

import java.util.List;

/**
 * 系统角色Service
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecRoleService {

    void save(SecRole secRole) throws XSBusinessException;

    void remove(SecRole secRole) throws XSBusinessException;

    void update(SecRole secRole) throws XSBusinessException;

    SecRole get(SecRole secRole) throws XSBusinessException;

    XSPageModel listAndCount(SecRole secRole);

    List<SecRole> listCombo(SecRole secRole);

    List<SecResource> treeResource(SecRole secRole) throws XSBusinessException;

    void authorizeResource(SecRole secRole) throws XSBusinessException;
}
