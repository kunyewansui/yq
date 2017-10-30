package com.xiaosuokeji.server.security.admin.dao;

import com.xiaosuokeji.server.security.admin.model.SecOrganization;
import com.xiaosuokeji.server.security.admin.model.SecRole;
import com.xiaosuokeji.server.security.admin.model.SecStaff;

import java.util.List;

/**
 * 系统用户Dao
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecStaffDao {

    int save(SecStaff secStaff);

    int saveStaffRole(SecStaff secStaff);

    int remove(SecStaff secStaff);

    int removeStaffRole(SecStaff secStaff);

    int removeStaffOrganization(SecStaff secStaff);

    int update(SecStaff secStaff);

    SecStaff get(SecStaff secStaff);

    SecStaff getByUsername(SecStaff secStaff);

    List<SecStaff> list(SecStaff secStaff);

    /**
     * 获取角色列表
     * @param secRole
     * @return 角色列表
     */
    List<SecRole> listRoleCombo(SecRole secRole);

    /**
     * 获取属于指定员工的角色列表
     * @param secStaff 参数id
     * @return 角色列表
     */
    List<SecRole> listRole(SecStaff secStaff);

    /**
     * 获取属于指定多个组织的角色列表
     * @param secStaff 参数organizationList
     * @return 角色列表
     */
    List<SecRole> listRoleByOrganization(SecStaff secStaff);

    /**
     * 获取属于指定员工的组织列表
     * @param secStaff 参数id
     * @return 组织列表
     */
    List<SecOrganization> listOrganization(SecStaff secStaff);

    Long count(SecStaff secStaff);
}
