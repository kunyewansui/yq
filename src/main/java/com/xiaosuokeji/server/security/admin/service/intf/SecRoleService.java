package com.xiaosuokeji.server.security.admin.service.intf;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecRole;
import com.xiaosuokeji.server.security.admin.model.SecStaff;
import org.springframework.security.core.Authentication;

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

    /**
     * 获取指定角色的资源树
     * @param secRole 参数id
     * @param secStaff 当前操作的用户
     * @return 资源树
     * @throws XSBusinessException 角色不存在
     */
    List treeResource(SecRole secRole, SecStaff secStaff) throws XSBusinessException;

    /**
     * 授予指定角色资源
     * @param secRole 参数id，resourceList
     * @param secStaff 当前操作的用户
     * @throws XSBusinessException 角色不存在
     */
    void authorizeResource(SecRole secRole, SecStaff secStaff) throws XSBusinessException;
}
