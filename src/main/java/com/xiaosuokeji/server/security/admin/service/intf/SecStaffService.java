package com.xiaosuokeji.server.security.admin.service.intf;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.security.admin.model.SecRole;
import com.xiaosuokeji.server.security.admin.model.SecStaff;

import java.util.List;

/**
 * 系统用户Service
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecStaffService {

    void save(SecStaff secStaff) throws XSBusinessException;

    void remove(SecStaff secStaff) throws XSBusinessException;

    void update(SecStaff secStaff) throws XSBusinessException;

    SecStaff get(SecStaff secStaff) throws XSBusinessException;

    SecStaff getByUsername(SecStaff secStaff);

    XSPageModel listAndCount(SecStaff secStaff);

    List<SecRole> listRole(SecStaff secStaff) throws XSBusinessException;

    void authorizeRole(SecStaff secStaff) throws XSBusinessException;
}
