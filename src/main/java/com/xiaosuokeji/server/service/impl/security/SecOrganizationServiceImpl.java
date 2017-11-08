package com.xiaosuokeji.server.service.impl.security;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.constant.security.SecOrganizationConsts;
import com.xiaosuokeji.server.dao.security.SecOrganizationDao;
import com.xiaosuokeji.server.model.security.SecOrganization;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.service.intf.security.SecOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        SecOrganization criteria = new SecOrganization();
        criteria.setName(secOrganization.getName());
        criteria.setParent(secOrganization.getParent());
        Long count = secOrganizationDao.count(criteria);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_EXIST);
        }
        //父级禁用则子级禁用
        if (secOrganization.getParent() != null && secOrganization.getParent().getId() != null
                && !secOrganization.getParent().getId().equals(0L)) {
            SecOrganization parent = secOrganizationDao.get(secOrganization.getParent());
            if (parent != null) {
                if (parent.getStatus().equals(0)) {
                    secOrganization.setStatus(0);
                }
            }
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
        //校验是否存在员工属于该组织
        Long staffCount = secOrganizationDao.countStaff(existent);
        if (staffCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_USED);
        }
        secOrganizationDao.removeOrganizationRole(existent);
        secOrganizationDao.remove(existent);
    }

    @Override
    @Transactional
    public void update(SecOrganization secOrganization) throws XSBusinessException {
        SecOrganization existent = get(secOrganization);
        if (secOrganization.getName() != null || secOrganization.getParent() != null) {
            SecOrganization criteria = new SecOrganization();
            criteria.setName(secOrganization.getName() == null ? existent.getName() : secOrganization.getName());
            criteria.setParent(secOrganization.getParent() == null ? existent.getParent() : secOrganization.getParent());
            List<SecOrganization> existents = secOrganizationDao.list(criteria);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(existent.getId());
                if (!isSelf) {
                    throw new XSBusinessException(SecOrganizationConsts.SEC_ORGANIZATION_EXIST);
                }
            }
        }
        secOrganizationDao.update(secOrganization);
        if (secOrganization.getStatus() != null) {
            List<SecOrganization> list = secOrganizationDao.listCombo(new SecOrganization());
            Map<Long, SecOrganization> map = XSTreeUtil.buildTree(list);
            SecOrganization latest = new SecOrganization();
            latest.setStatus(secOrganization.getStatus());
            //禁用则所有子级也禁用，启用则直属父级和所有子级也启用
            if (secOrganization.getStatus().equals(0)) {
                List<SecOrganization> subTreeList = XSTreeUtil.listSubTree(map.get(existent.getId()));
                latest.setList(subTreeList);
            }
            else {
                List<SecOrganization> treePath = XSTreeUtil.getTreePath(map, map.get(existent.getId()));
                latest.setList(treePath);
                List<SecOrganization> subTreeList = XSTreeUtil.listSubTree(map.get(existent.getId()));
                latest.getList().addAll(subTreeList);
            }
            secOrganizationDao.batchUpdate(latest);
        }
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
    public List<SecOrganization> tree(SecOrganization secOrganization) {
        List<SecOrganization> list = secOrganizationDao.listCombo(secOrganization);
        XSTreeUtil.buildTree(list);
        return XSTreeUtil.getSubTrees(list, new SecOrganization(0L));
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
        //不能授予超级管理员角色
        for (Iterator<SecRole> iterator = secOrganization.getRoleList().iterator(); iterator.hasNext();) {
            if (iterator.next().getId().equals(1L)) {
                iterator.remove();
            }
        }
        secOrganizationDao.removeOrganizationRole(existent);
        if (secOrganization.getRoleList().size() > 0) {
            secOrganizationDao.saveOrganizationRole(secOrganization);
        }
    }
}
