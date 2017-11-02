package com.xiaosuokeji.server.security.admin.service.intf;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.security.admin.model.SecOrganization;
import com.xiaosuokeji.server.security.admin.model.SecRole;

import java.util.List;

/**
 * 系统组织Service
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecOrganizationService {

    void save(SecOrganization secOrganization) throws XSBusinessException;

    void remove(SecOrganization secOrganization) throws XSBusinessException;

    void update(SecOrganization secOrganization) throws XSBusinessException;

    SecOrganization get(SecOrganization secOrganization) throws XSBusinessException;

    XSPageModel<SecOrganization> listAndCount(SecOrganization secOrganization);

    /**
     * 获取指定组织的角色列表
     * @param secOrganization 参数id
     * @return 角色列表
     * @throws XSBusinessException 组织不存在
     */
    List<SecRole> listRole(SecOrganization secOrganization) throws XSBusinessException;

    /**
     * 授予指定组织角色
     * @param secOrganization 参数id，roleList
     * @throws XSBusinessException 组织不存在
     */
    void authorizeRole(SecOrganization secOrganization) throws XSBusinessException;
}
