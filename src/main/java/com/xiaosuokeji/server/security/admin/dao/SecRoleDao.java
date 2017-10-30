package com.xiaosuokeji.server.security.admin.dao;

import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecRole;

import java.util.List;

/**
 * 系统角色
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecRoleDao {

    int save(SecRole secRole);

    int saveRoleRes(SecRole secRole);

    int remove(SecRole secRole);

    int removeRoleRes(SecRole secRole);

    int update(SecRole secRole);

    SecRole get(SecRole secRole);

    List<SecRole> list(SecRole secRole);

    /**
     * 获取资源列表
     * @param secResource
     * @return 资源列表
     */
    List<SecResource> listResourceCombo(SecResource secResource);

    /**
     * 获取属于指定角色的资源列表
     * @param secRole 参数id
     * @return 资源列表
     */
    List<SecResource> listResource(SecRole secRole);

    Long count(SecRole secRole);

    /**
     * 获取拥有指定角色的员工数量
     * @param secRole 参数id
     * @return 员工数量
     */
    Long countStaff(SecRole secRole);

    /**
     * 获取拥有指定角色的组织数量
     * @param secRole 参数id
     * @return 组织数量
     */
    Long countOrganization(SecRole secRole);
}
