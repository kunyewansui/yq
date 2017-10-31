package com.xiaosuokeji.server.security.admin.dao;

import com.xiaosuokeji.server.security.admin.model.SecOrganization;
import com.xiaosuokeji.server.security.admin.model.SecRole;

import java.util.List;

/**
 * 系统组织Dao
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecOrganizationDao {

    int save(SecOrganization secOrganization);

    int saveOrganizationRole(SecOrganization secOrganization);

    int remove(SecOrganization secOrganization);

    int removeOrganizationRole(SecOrganization secOrganization);

    int update(SecOrganization secOrganization);

    SecOrganization get(SecOrganization secOrganization);

    List<SecOrganization> list(SecOrganization secOrganization);

    /**
     * 获取角色列表
     * @param secRole
     * @return 角色列表
     */
    List<SecRole> listRoleCombo(SecRole secRole);

    /**
     * 获取属于指定组织的角色列表
     * @param secOrganization 参数id
     * @return 角色列表
     */
    List<SecRole> listRole(SecOrganization secOrganization);

    Long count(SecOrganization secOrganization);

    /**
     * 获取拥有指定组织的员工数量
     * @param secOrganization 参数id
     * @return 员工数量
     */
    Long countStaff(SecOrganization secOrganization);
}
