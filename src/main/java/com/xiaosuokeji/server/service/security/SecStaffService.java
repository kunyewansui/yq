package com.xiaosuokeji.server.service.security;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSMd5Util;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.constant.security.SecStaffConsts;
import com.xiaosuokeji.server.dao.security.SecStaffDao;
import com.xiaosuokeji.server.model.security.SecOrganization;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.model.security.SecStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 系统用户ServiceImpl
 * Created by xuxiaowei on 2017/10/27.
 */
@Service
public class SecStaffService {

    @Autowired
    private SecStaffDao secStaffDao;

    public void save(SecStaff secStaff) throws Exception {
        SecStaff criteria = new SecStaff();
        criteria.setUsername(secStaff.getUsername());
        SecStaff existent = secStaffDao.getByUsername(criteria);
        if (existent != null) {
            throw new XSBusinessException(SecStaffConsts.SEC_STAFF_EXIST);
        }
        secStaff.setPassword(XSMd5Util.encode(secStaff.getPassword()));
        secStaffDao.save(secStaff);
    }

    @Transactional
    public void remove(SecStaff secStaff) throws XSBusinessException {
        SecStaff existent = get(secStaff);
        secStaffDao.removeStaffOrganization(existent);
        secStaffDao.removeStaffRole(existent);
        secStaffDao.remove(existent);
    }

    public void update(SecStaff secStaff) throws Exception {
        SecStaff existent = get(secStaff);
        if (secStaff.getUsername() != null) {
            SecStaff criteria = new SecStaff();
            criteria.setUsername(secStaff.getUsername());
            existent = secStaffDao.getByUsername(criteria);
            if (existent != null && !existent.getId().equals(secStaff.getId())) {
                throw new XSBusinessException(SecStaffConsts.SEC_STAFF_EXIST);
            }
        }
        if (secStaff.getPassword() != null) {
            secStaff.setPassword(XSMd5Util.encode(secStaff.getPassword()));
        }
        secStaffDao.update(secStaff);
    }

    public SecStaff get(SecStaff secStaff) throws XSBusinessException {
        SecStaff existent = secStaffDao.get(secStaff);
        if (existent == null) {
            throw new XSBusinessException(SecStaffConsts.SEC_STAFF_NOT_EXIST);
        }
        existent.setPassword(null);
        return existent;
    }

    public SecStaff getByUsername(SecStaff secStaff) {
        SecStaff existent = secStaffDao.getByUsername(secStaff);
        if (existent != null && existent.isEnabled()) {
            //获取组织列表并校验组织状态，若员工不属于任何组织或只要其所处的任意一个组织启用即可
            List<SecOrganization> organizationList = secStaffDao.listOrganization(existent);
            if (organizationList.size() > 0) {
                int i = 0;
                for (; i < organizationList.size(); ++i) {
                    if (organizationList.get(i).getStatus().equals(1)) {
                        break;
                    }
                }
                if (i >= organizationList.size()) {
                    existent.setStatus(0);
                }
            }
            if (existent.isEnabled()) {
                existent.setOrganizationList(organizationList);
                //获取角色列表并删除被禁用的角色
                List<SecRole> roleList = secStaffDao.listRole(existent);
                for (Iterator<SecRole> iterator = roleList.iterator(); iterator.hasNext();) {
                    if (iterator.next().getStatus().equals(0)) {
                        iterator.remove();
                    }
                }
                if (organizationList.size() > 0) {
                    //获取启用的组织列表
                    List<SecOrganization> enabledOrg = new ArrayList<>();
                    for (SecOrganization item : organizationList) {
                        if (item.getStatus().equals(1)) {
                            enabledOrg.add(item);
                        }
                    }
                    if (enabledOrg.size() > 0) {
                        //获取启用的组织的角色列表并删除被禁用的角色
                        SecStaff existOrg = new SecStaff();
                        existOrg.setOrganizationList(enabledOrg);
                        List<SecRole> orgRoleList = secStaffDao.listRoleByOrganization(existOrg);
                        for (Iterator<SecRole> iterator = orgRoleList.iterator(); iterator.hasNext();) {
                            if (iterator.next().getStatus().equals(0)) {
                                iterator.remove();
                            }
                        }
                        //将组织拥有的角色加入到角色列表
                        for (Iterator<SecRole> iterator = orgRoleList.iterator(); iterator.hasNext();) {
                            SecRole secRole = iterator.next();
                            for (SecRole item : roleList) {
                                if (secRole.getId().equals(item.getId())) {
                                    iterator.remove();
                                }
                            }
                        }
                        roleList.addAll(orgRoleList);
                    }
                }
                existent.setRoleList(roleList);
            }
        }
        return existent;
    }

    public XSPageModel listAndCount(SecStaff secStaff) {
        secStaff.setDefaultSort("id", "DESC");
        return XSPageModel.build(secStaffDao.list(secStaff), secStaffDao.count(secStaff));
    }

    public List<SecRole> listRole(SecStaff secStaff) throws XSBusinessException {
        SecStaff existent = get(secStaff);
        List<SecRole> roleList = secStaffDao.listRoleCombo(new SecRole());
        List<SecRole> ownedRoleList = secStaffDao.listRole(existent);
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

    @Transactional
    public void authorizeRole(SecStaff secStaff) throws XSBusinessException {
        SecStaff existent = get(secStaff);
        secStaffDao.removeStaffRole(existent);
        if (secStaff.getRoleList().size() > 0) {
            secStaffDao.saveStaffRole(secStaff);
        }
    }

    public List treeOrganization(SecStaff secStaff) throws XSBusinessException {
        SecStaff existent = get(secStaff);
        List<SecOrganization> orgList = secStaffDao.listOrganizationCombo(new SecOrganization());
        List<SecOrganization> ownedOrgList = secStaffDao.listOrganization(existent);
        for (Iterator<SecOrganization> iterator = orgList.iterator(); iterator.hasNext();) {
            SecOrganization item = iterator.next();
            for (SecOrganization owned : ownedOrgList) {
                if (item.getId().equals(owned.getId())) {
                    item.setChecked(1);
                    break;
                }
            }
        }
        XSTreeUtil.buildTree(orgList);
        return XSTreeUtil.getSubTrees(orgList, new SecOrganization(0L));
    }

    @Transactional
    public void authorizeOrganization(SecStaff secStaff) throws XSBusinessException {
        SecStaff existent = get(secStaff);
        secStaffDao.removeStaffOrganization(existent);
        if (secStaff.getOrganizationList().size() > 0) {
            secStaffDao.saveStaffOrganization(secStaff);
        }
    }
}
