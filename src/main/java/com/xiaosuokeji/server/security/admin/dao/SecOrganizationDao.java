package com.xiaosuokeji.server.security.admin.dao;

import com.xiaosuokeji.server.security.admin.model.SecOrganization;

import java.util.List;

/**
 * 系统组织Dao
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecOrganizationDao {

    int save(SecOrganization secOrganization);

    int remove(SecOrganization secOrganization);

    int update(SecOrganization secOrganization);

    SecOrganization get(SecOrganization secOrganization);

    List<SecOrganization> list(SecOrganization secOrganization);

    List<SecOrganization> listCombo(SecOrganization secOrganization);

    Long count(SecOrganization secOrganization);
}
