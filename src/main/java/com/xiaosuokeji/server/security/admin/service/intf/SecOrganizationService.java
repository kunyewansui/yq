package com.xiaosuokeji.server.security.admin.service.intf;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.security.admin.model.SecOrganization;

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

    XSPageModel listAndCount(SecOrganization secOrganization);

    List<SecOrganization> tree(SecOrganization secOrganization);
}
