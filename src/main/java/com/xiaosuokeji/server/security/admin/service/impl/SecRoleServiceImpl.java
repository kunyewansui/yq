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
import java.util.List;

/**
 * 系统角色ServiceImpl
 * Created by xuxiaowei on 2017/10/27.
 */
@Service
public class SecRoleServiceImpl implements SecRoleService {

    @Autowired
    private SecRoleDao secRoleDao;

    @Autowired
    private SecResourceDao secResourceDao;

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
    public void remove(SecRole secRole) throws XSBusinessException {
        secRoleDao.remove(secRole);
    }

    @Override
    public void update(SecRole secRole) throws XSBusinessException {
        if (secRole.getName() != null) {
            SecRole existent = new SecRole();
            existent.setName(secRole.getName());
            Long count = secRoleDao.count(existent);
            if (count.compareTo(0L) > 0) {
                throw new XSBusinessException(SecRoleConsts.SEC_ROLE_EXIST);
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
        return secRoleDao.get(secRole);
    }

    @Override
    public XSPageModel listAndCount(SecRole secRole) {
        secRole.setDefaultSort("create_time", "DESC");
        return XSPageModel.build(secRoleDao.list(secRole), secRoleDao.count(secRole));
    }

    @Override
    public List<SecRole> listCombo(SecRole secRole) {
        secRole.setDefaultSort("create_time", "DESC");
        return secRoleDao.listCombo(secRole);
    }

    @Override
    public List<SecResource> treeResource(SecRole secRole) throws XSBusinessException {
        SecRole existent = secRoleDao.get(secRole);
        if (existent == null) {
            throw new XSBusinessException(SecRoleConsts.SEC_ROLE_NOT_EXIST);
        }
        List<SecResource> resourceList = secResourceDao.listCombo(new SecResource());
        List<SecResource> ownedResourceList = secRoleDao.listResource(secRole);
        for (SecResource item : resourceList) {
            for (SecResource ownd : ownedResourceList) {
                if (item.getId().equals(ownd.getId())) {
                    item.setChecked(1);
                }
            }
        }
        XSTreeUtil.buildTree(resourceList);
        List<SecResource> trees = new ArrayList<>();
        for (SecResource item : resourceList) {
            if (item.getParent() == null) {
                trees.add(item);
            }
        }
        return trees;
    }

    @Override
    @Transactional
    public void authorizeResource(SecRole secRole) throws XSBusinessException {
        SecRole existent = secRoleDao.get(secRole);
        if (existent == null) {
            throw new XSBusinessException(SecRoleConsts.SEC_ROLE_NOT_EXIST);
        }
        secRoleDao.removeRoleRes(existent);
        secRoleDao.saveRoleRes(secRole);
    }
}
