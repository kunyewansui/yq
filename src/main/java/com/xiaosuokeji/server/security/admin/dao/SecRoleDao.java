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

    List<SecRole> listCombo(SecRole secRole);

    List<SecResource> listResource(SecRole secRole);

    Long count(SecRole secRole);

    Long countStaff(SecRole secRole);

    Long countOrganization(SecRole secRole);
}
