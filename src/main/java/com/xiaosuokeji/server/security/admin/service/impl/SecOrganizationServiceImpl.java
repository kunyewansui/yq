package com.xiaosuokeji.server.security.admin.service.impl;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.security.admin.constant.SecOrganizationConsts;
import com.xiaosuokeji.server.security.admin.dao.SecOrganizationDao;
import com.xiaosuokeji.server.security.admin.model.SecOrganization;
import com.xiaosuokeji.server.security.admin.service.intf.SecOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void remove(SecOrganization secOrganization) throws XSBusinessException {
        SecOrganization existent = new SecOrganization();
        existent.setParent(secOrganization);
        Long count = secOrganizationDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_USED);
        }
        secOrganizationDao.remove(secOrganization);
    }

    @Override
    public void update(SecOrganization secOrganization) throws XSBusinessException {
        if (secOrganization.getName() != null || secOrganization.getParent() != null) {
            SecOrganization existent = new SecOrganization();
            existent.setName(secOrganization.getName());
            existent.setParent(secOrganization.getParent());
            Long count = secOrganizationDao.count(existent);
            if (count.compareTo(0L) > 0) {
                throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_EXIST);
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
        return secOrganizationDao.get(secOrganization);
    }

    @Override
    public XSPageModel listAndCount(SecOrganization secOrganization) {
        secOrganization.setDefaultSort("create_time", "DESC");
        return XSPageModel.build(secOrganizationDao.list(secOrganization), secOrganizationDao.count(secOrganization));
    }

    @Override
    public List<SecOrganization> tree(SecOrganization secOrganization) {
        secOrganization.setDefaultSort("seq", "DESC");
        List<SecOrganization> list = secOrganizationDao.listCombo(secOrganization);
        XSTreeUtil.buildTree(list);
        List<SecOrganization> trees = new ArrayList<>();
        //如果未指定父级则返回所有分类，否则返回指定父级下的所有分类
        if (secOrganization.getParent() != null && secOrganization.getParent().getId() != null) {
            for (SecOrganization item : list) {
                boolean isChild = item.getParent() != null && item.getParent().getId() != null &&
                        item.getParent().getId().equals(secOrganization.getParent().getId());
                if (isChild) {
                    trees.add(item);
                }
            }
        } else {
            for (SecOrganization item : list) {
                if (item.getParent() == null) {
                    trees.add(item);
                }
            }
        }
        return trees;
    }
}
