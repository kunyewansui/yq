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
    public void authorizeResource(SecRole secRole) throws XSBusinessException {
        SecRole existent = secRoleDao.get(secRole);
        if (existent == null) {
            throw new XSBusinessException(SecRoleConsts.SEC_ROLE_NOT_EXIST);
        }
        List<SecResource> oldResList = secRoleDao.listResource(secRole);
        List<SecResource> newResList = new ArrayList<>();
        List<SecResource> noChangedResList = new ArrayList<>();
        //查询出新增的资源列表和未变动的资源列表
        if (secRole.getResourceList() != null) {
            for (int i = 0; i < secRole.getResourceList().size(); ++i) {
                int j = 0;
                for (; j < oldResList.size(); ++j) {
                    if (secRole.getResourceList().get(i).getId().equals(oldResList.get(j).getId())) {
                        noChangedResList.add(oldResList.get(j));
                        break;
                    }
                }
                if (j >= oldResList.size()) newResList.add(secRole.getResourceList().get(i));
            }
        }
        //新增资源
        if (newResList.size() > 0) {
            secRole.setResourceList(newResList);
            secRoleDao.saveRoleRes(secRole);
        }
        //从旧资源中去除未变动的资源从而获取需要删除的资源
        oldResList.removeAll(noChangedResList);
        //删除资源
        if (oldResList.size() > 0) {
            secRole.setResourceList(oldResList);
            secRoleDao.removeRoleRes(secRole);
        }
    }
}
