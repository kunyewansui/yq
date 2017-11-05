package com.xiaosuokeji.server.service.impl.security;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.constant.security.SecRoleConsts;
import com.xiaosuokeji.server.dao.security.SecRoleDao;
import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.service.intf.security.SecRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        SecRole criteria = new SecRole();
        criteria.setName(secRole.getName());
        Long count = secRoleDao.count(criteria);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecRoleConsts.SEC_ROLE_EXIST);
        }
        secRoleDao.save(secRole);
    }

    @Override
    @Transactional
    public void remove(SecRole secRole) throws XSBusinessException {
        SecRole existent = get(secRole);
        //校验是否存在员工拥有该角色
        Long staffCount = secRoleDao.countStaff(existent);
        if (staffCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecRoleConsts.SEC_ROLE_USED);
        }
        //校验是否存在组织拥有该角色
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
            SecRole criteria = new SecRole();
            criteria.setName(secRole.getName());
            List<SecRole> existents = secRoleDao.list(criteria);
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
    public List treeResource(SecRole secRole, SecStaff secStaff) throws XSBusinessException {
        SecRole existent = get(secRole);
        SecResource criteria = new SecResource();
        criteria.setDefaultSort("seq", "DESC");
        List<SecResource> resourceList = secRoleDao.listResourceCombo(criteria);
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
        //非超级管理员不能够操作不可分配资源
        if (!secStaff.getId().equals(1L)) {
            for (Iterator<SecResource> iterator = resourceList.iterator(); iterator.hasNext();) {
                if (iterator.next().getAssign().equals(0)) {
                    iterator.remove();
                }
            }
        }
        XSTreeUtil.buildTree(resourceList);
        return XSTreeUtil.getSubTrees(resourceList, null);
    }

    @Override
    @Transactional
    public void authorizeResource(SecRole secRole, SecStaff secStaff) throws XSBusinessException {
        SecRole existent = get(secRole);
        List<SecResource> resourceList = secRoleDao.listResourceCombo(new SecResource());
        //非超级管理员不能够操作不可分配资源
        if (!secStaff.getId().equals(1L)) {
            for (Iterator<SecResource> iterator = secRole.getResourceList().iterator(); iterator.hasNext();) {
                SecResource secResource = iterator.next();
                for (SecResource item : resourceList) {
                    if (secResource.getId().equals(item.getId()) && item.getAssign().equals(0)) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
        //加入类型为url的资源
        Map<Long, SecResource> map = XSTreeUtil.buildTree(resourceList);
        List<SecResource> urlResource = new ArrayList<>();
        for (Iterator<SecResource> iterator = secRole.getResourceList().iterator(); iterator.hasNext();) {
            SecResource secResource = map.get(iterator.next().getId());
            if (secResource != null && secResource.getChildren() != null) {
                for(SecResource item : secResource.getChildren()) {
                    if (item.getType().equals(2) && item.getAssign().equals(1)) {
                        urlResource.add(item);
                    }
                }
            }
        }
        secRole.getResourceList().addAll(urlResource);
        secRoleDao.removeRoleRes(existent);
        if (secRole.getResourceList().size() > 0) {
            secRoleDao.saveRoleRes(secRole);
        }
    }
}
