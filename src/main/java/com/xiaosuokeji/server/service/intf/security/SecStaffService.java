package com.xiaosuokeji.server.service.intf.security;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.model.security.SecStaff;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 系统用户Service
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecStaffService {

    void save(SecStaff secStaff) throws Exception;

    void remove(SecStaff secStaff) throws XSBusinessException;

    void update(SecStaff secStaff) throws Exception;

    SecStaff get(SecStaff secStaff) throws XSBusinessException;

    SecStaff getByUsername(SecStaff secStaff);

    XSPageModel listAndCount(SecStaff secStaff);

    /**
     * 获取指定员工的角色列表
     * @param secStaff 参数id
     * @return 角色列表
     * @throws XSBusinessException 员工不存在
     */
    List<SecRole> listRole(SecStaff secStaff) throws XSBusinessException;

    /**
     * 授予指定员工角色
     * @param secStaff 参数id，roleList
     * @throws XSBusinessException 员工不存在
     */
    void authorizeRole(SecStaff secStaff) throws XSBusinessException;

    /**
     * 获取指定员工的组织树
     * @param secStaff 参数id
     * @return 组织树
     * @throws XSBusinessException 员工不存在
     */
    List treeOrganization(SecStaff secStaff) throws XSBusinessException;

    /**
     * 使指定员工加入组织
     * @param secStaff 参数id，organizationList
     * @throws XSBusinessException 员工不存在
     */
    void authorizeOrganization(SecStaff secStaff) throws XSBusinessException;
}
