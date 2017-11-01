package com.xiaosuokeji.server.security.admin.service.impl;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.intf.XSTreeable;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.security.admin.constant.SecResourceConsts;
import com.xiaosuokeji.server.security.admin.dao.SecResourceDao;
import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecRole;
import com.xiaosuokeji.server.security.admin.service.intf.SecResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统资源ServiceImpl
 * Created by xuxiaowei on 2017/10/27.
 */
@Service
public class SecResourceServiceImpl implements SecResourceService {

    @Autowired
    private SecResourceDao secResourceDao;

    @Override
    @Transactional
    public void save(SecResource secResource) throws XSBusinessException {
        SecResource existent = new SecResource();
        existent.setKey(secResource.getKey());
        Long count = secResourceDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_EXIST);
        }
        //父级不可分配则子级不可分配
        if (secResource.getParent() != null) {
            SecResource parent = secResourceDao.get(secResource.getParent());
            if (parent != null) {
                if (parent.getAssign().equals(0)) {
                    secResource.setAssign(0);
                }
            }
        }
        secResourceDao.save(secResource);
        secResourceDao.saveSuperiorRes(secResource);
    }

    @Override
    @Transactional
    public void remove(SecResource secResource) throws XSBusinessException {
        SecResource existent = get(secResource);
        SecResource children = new SecResource();
        children.setParent(secResource);
        Long childrenCount = secResourceDao.count(children);
        if (childrenCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_USED);
        }
        //校验是否存在角色拥有该资源
        Long roleCount = secResourceDao.countRole(existent);
        if (roleCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_USED);
        }
        secResourceDao.removeSuperiorRes(existent);
        secResourceDao.remove(existent);
    }

    @Override
    @Transactional
    public void update(SecResource secResource) throws XSBusinessException {
        SecResource existent = get(secResource);
        if (secResource.getKey() != null) {
            SecResource existRes = new SecResource();
            existRes.setKey(secResource.getKey());
            List<SecResource> existents = secResourceDao.list(existRes);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(existent.getId());
                if (!isSelf) {
                    throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_EXIST);
                }
            }
        }
        secResourceDao.update(secResource);
        if (secResource.getAssign() != null) {
            List<SecResource> list = secResourceDao.listCombo(new SecResource());
            Map<Long, XSTreeable<Long>> map = XSTreeUtil.buildTree(list);
            SecResource latestRes = new SecResource();
            latestRes.setAssign(secResource.getAssign());
            //不可分配则所有子级也不可分配，可分配则所有父级也可分配
            if (secResource.getAssign().equals(0)) {
                List<XSTreeable<Long>> subTreeList = XSTreeUtil.listSubTree(map.get(existent.getId()));
                latestRes.setTreeableList(subTreeList);
            }
            else {
                List<XSTreeable<Long>> treePath = XSTreeUtil.getTreePath(map, map.get(existent.getId()));
                latestRes.setTreeableList(treePath);
            }
            secResourceDao.batchUpdate(latestRes);
        }
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
    public List<SecRole> listRoleByRequest(SecResource secResource) {
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
