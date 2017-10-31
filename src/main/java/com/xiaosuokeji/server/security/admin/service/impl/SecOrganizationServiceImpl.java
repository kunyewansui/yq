package com.xiaosuokeji.server.security.admin.service.impl;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.security.admin.constant.SecOrganizationConsts;
import com.xiaosuokeji.server.security.admin.dao.SecOrganizationDao;
import com.xiaosuokeji.server.security.admin.model.SecOrganization;
import com.xiaosuokeji.server.security.admin.model.SecRole;
import com.xiaosuokeji.server.security.admin.model.SecStaff;
import com.xiaosuokeji.server.security.admin.service.intf.SecOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 系统组织ServiceImpl
 * Created by xuxiaowei on 2017/10/27.
 */
@Service
public class SecOrganizationServiceImpl implements SecOrganizationService {

    @Autowired
    private SecOrganizationDao secOrganizationDao;

    @Override
    public void save(SecOrganization secOrganization) throws XSBusinessException {
        SecOrganization existent = new SecOrganization();
        existent.setName(secOrganization.getName());
        existent.setParent(secOrganization.getParent());
        Long count = secOrganizationDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_EXIST);
        }
        secOrganizationDao.save(secOrganization);
    }

    @Override
    @Transactional
    public void remove(SecOrganization secOrganization) throws XSBusinessException {
        SecOrganization existent = get(secOrganization);
        SecOrganization children = new SecOrganization();
        children.setParent(secOrganization);
        Long childrenCount = secOrganizationDao.count(children);
        if (childrenCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_USED);
        }
        Long staffCount = secOrganizationDao.countStaff(existent);
        if (staffCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_USED);
        }
        secOrganizationDao.removeOrganizationRole(existent);
        secOrganizationDao.remove(existent);
    }

    @Override
    public void update(SecOrganization secOrganization) throws XSBusinessException {
        SecOrganization existent = get(secOrganization);
        if (secOrganization.getName() != null || secOrganization.getParent() != null) {
            SecOrganization existOrg = new SecOrganization();
            existOrg.setName(secOrganization.getName() == null ? existent.getName() : secOrganization.getName());
            existOrg.setParent(secOrganization.getParent() == null ? existent.getParent() : secOrganization.getParent());
            List<SecOrganization> existents = secOrganizationDao.list(existOrg);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(existent.getId());
                if (!isSelf) {
                    throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_EXIST);
                }
            }
        }
        secOrganizationDao.update(secOrganization);
    }

    @Override
    public SecOrganization get(SecOrganization secOrganization) throws XSBusinessException {
        SecOrganization existent = secOrganizationDao.get(secOrganization);
        if (existent == null) {
            throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public XSPageModel listAndCount(SecOrganization secOrganization) {
        secOrganization.setDefaultSort("id", "DESC");
        return XSPageModel.build(secOrganizationDao.list(secOrganization), secOrganizationDao.count(secOrganization));
    }

    @Override
    public List<SecRole> listRole(SecOrganization secOrganization) throws XSBusinessException {
        SecOrganization existent = get(secOrganization);
        List<SecRole> roleList = secOrganizationDao.listRoleCombo(new SecRole());
        List<SecRole> ownedRoleList = secOrganizationDao.listRole(existent);
        for (Iterator<SecRole> iterator = roleList.iterator(); iterator.hasNext();) {
            SecRole item = iterator.next();
            for (SecRole owned : ownedRoleList) {
                if (item.getId().equals(owned.getId())) {
                    item.setChecked(1);
                    break;
                }
            }
        }
        return roleList;
    }

    @Override
    @Transactional
    public void authorizeRole(SecOrganization secOrganization) throws XSBusinessException {
        SecOrganization existent = get(secOrganization);
        secOrganizationDao.removeOrganizationRole(existent);
        secOrganizationDao.saveOrganizationRole(secOrganization);
    }
}
