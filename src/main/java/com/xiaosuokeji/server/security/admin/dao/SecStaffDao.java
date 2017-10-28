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

    int update(SecStaff secStaff);

    SecStaff get(SecStaff secStaff);

    SecStaff getByUsername(SecStaff secStaff);

    List<SecStaff> list(SecStaff secStaff);

    List<SecRole> listRole(SecStaff secStaff);

    List<SecOrganization> listOrganization(SecStaff secStaff);

    Long count(SecStaff secStaff);
}
