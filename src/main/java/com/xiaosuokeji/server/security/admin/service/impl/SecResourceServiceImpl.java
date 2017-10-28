package com.xiaosuokeji.server.security.admin.service.impl;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.security.admin.constant.SecResourceConsts;
import com.xiaosuokeji.server.security.admin.dao.SecResourceDao;
import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecRole;
import com.xiaosuokeji.server.security.admin.service.intf.SecResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 系统资源ServiceImpl
 * Created by xuxiaowei on 2017/10/27.
 */
@Service
public class SecResourceServiceImpl implements SecResourceService {

    @Autowired
    private SecResourceDao secResourceDao;

    @Override
    public void save(SecResource secResource) throws XSBusinessException {
        SecResource existent = new SecResource();
        existent.setKey(secResource.getKey());
        Long count = secResourceDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_EXIST);
        }
        secResourceDao.save(secResource);
    }

    @Override
    public void remove(SecResource secResource) throws XSBusinessException {
        SecResource existent = new SecResource();
        existent.setParent(secResource);
        Long count = secResourceDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_USED);
        }
        //校验是否存在角色拥有该资源
        existent = new SecResource(secResource.getId());
        Long roleCount = secResourceDao.countRole(existent);
        if (roleCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_USED);
        }
        secResourceDao.remove(secResource);
    }

    @Override
    public void update(SecResource secResource) throws XSBusinessException {
        if (secResource.getKey() != null) {
            SecResource existent = new SecResource();
            existent.setKey(secResource.getKey());
            Long count = secResourceDao.count(existent);
            if (count.compareTo(0L) > 0) {
                throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_EXIST);
            }
        }
        secResourceDao.update(secResource);
    }

    @Override
    public SecResource get(SecResource secResource) throws XSBusinessException {
        SecResource existent = secResourceDao.get(secResource);
        if (existent == null) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_NOT_EXIST);
        }
        return secResourceDao.get(secResource);
    }

    @Override
    public XSPageModel listAndCount(SecResource secResource) {
        secResource.setDefaultSort(new String[]{"type", "seq"}, new String[]{"ASC", "DESC"});
        return XSPageModel.build(secResourceDao.list(secResource), secResourceDao.count(secResource));
    }

    @Override
    public List<SecResource> tree(SecResource secResource) {
        secResource.setDefaultSort("seq", "DESC");
        List<SecResource> list = secResourceDao.listCombo(secResource);
        //不展示url类型的资源
        for (Iterator<SecResource> iterator = list.iterator(); iterator.hasNext();) {
            if (iterator.next().getType().equals(2)) {
                iterator.remove();
            }
        }
        XSTreeUtil.buildTree(list);
        List<SecResource> trees = new ArrayList<>();
        //如果未指定父级则返回所有分类，否则返回指定父级下的所有分类
        if (secResource.getParent() != null && secResource.getParent().getId() != null) {
            for (SecResource item : list) {
                boolean isChild = item.getParent() != null && item.getParent().getId() != null &&
                        item.getParent().getId().equals(secResource.getParent().getId());
                if (isChild) {
                    trees.add(item);
                }
            }
        } else {
            for (SecResource item : list) {
                if (item.getParent() == null) {
                    trees.add(item);
                }
            }
        }
        return trees;
    }

    @Override
    public List<SecRole> listRole(SecResource secResource) {
        List<SecResource> resourceList = secResourceDao.listByRequest(secResource);
        if (resourceList.size() > 0) {
            SecResource existent = new SecResource();
            existent.setList(resourceList);
            List<SecRole> roleList = secResourceDao.listRole(existent);
            return roleList;
        }
        return new ArrayList<>();
    }
}
