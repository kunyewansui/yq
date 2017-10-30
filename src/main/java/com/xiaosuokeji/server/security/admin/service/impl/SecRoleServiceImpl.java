package com.xiaosuokeji.server.security.admin.service.impl;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.security.admin.constant.SecRoleConsts;
import com.xiaosuokeji.server.security.admin.dao.SecResourceDao;
import com.xiaosuokeji.server.security.admin.dao.SecRoleDao;
import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecRole;
import com.xiaosuokeji.server.security.admin.service.intf.SecRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 系统角色ServiceImpl
 * Created by xuxiaowei on 2017/10/27.
 */
@Service
public class SecRoleServiceImpl implements SecRoleService {

    @Autowired
    private SecRoleDao secRoleDao;

    @Override
    public void save(SecRole secRole) throws XSBusinessException {
        SecRole existent = new SecRole();
        existent.setName(secRole.getName());
        Long count = secRoleDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecRoleConsts.SEC_ROLE_EXIST);
        }
        secRoleDao.save(secRole);
    }

    @Override
    @Transactional
    public void remove(SecRole secRole) throws XSBusinessException {
        SecRole existent = get(secRole);
        Long staffCount = secRoleDao.countStaff(existent);
        if (staffCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecRoleConsts.SEC_ROLE_USED);
        }
        Long organizationCount = secRoleDao.countOrganization(existent);
        if (organizationCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecRoleConsts.SEC_ROLE_USED);
        }
        secRoleDao.removeRoleRes(existent);
        secRoleDao.remove(existent);
    }

    @Override
    public void update(SecRole secRole) throws XSBusinessException {
        get(secRole);
        if (secRole.getName() != null) {
            SecRole existent = new SecRole();
            existent.setName(secRole.getName());
            List<SecRole> existents = secRoleDao.list(existent);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(secRole.getId());
                if (!isSelf) {
                    throw new XSBusinessException(SecRoleConsts.SEC_ROLE_EXIST);
                }
            }
        }
        secRoleDao.update(secRole);
    }

    @Override
    public SecRole get(SecRole secRole) throws XSBusinessException {
        SecRole existent = secRoleDao.get(secRole);
        if (existent == null) {
            throw new XSBusinessException(SecRoleConsts.SEC_ROLE_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public XSPageModel listAndCount(SecRole secRole) {
        secRole.setDefaultSort("id", "DESC");
        return XSPageModel.build(secRoleDao.list(secRole), secRoleDao.count(secRole));
    }

    @Override
    public List treeResource(SecRole secRole) throws XSBusinessException {
        SecRole existent = get(secRole);
        SecResource existentRes = new SecResource();
        existentRes.setDefaultSort("seq", "DESC");
        List<SecResource> resourceList = secRoleDao.listResourceCombo(existentRes);
        List<SecResource> ownedResourceList = secRoleDao.listResource(existent);
        for (Iterator<SecResource> iterator = resourceList.iterator(); iterator.hasNext();) {
            SecResource item = iterator.next();
            //不展示类型为url的资源
            if (item.getType().equals(2)) {
                iterator.remove();
                continue;
            }
            for (SecResource owned : ownedResourceList) {
                if (item.getId().equals(owned.getId())) {
                    item.setChecked(1);
                    break;
                }
            }
        }
        XSTreeUtil.buildTree(resourceList);
        return XSTreeUtil.getSubTrees(resourceList, null);
    }

    @Override
    @Transactional
    public void authorizeResource(SecRole secRole) throws XSBusinessException {
        SecRole existent = get(secRole);
        secRoleDao.removeRoleRes(existent);
        secRoleDao.saveRoleRes(secRole);
    }
}
