package com.xiaosuokeji.server.security.admin.service.impl;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.security.admin.constant.SecStaffConsts;
import com.xiaosuokeji.server.security.admin.dao.SecStaffDao;
import com.xiaosuokeji.server.security.admin.model.SecOrganization;
import com.xiaosuokeji.server.security.admin.model.SecRole;
import com.xiaosuokeji.server.security.admin.model.SecStaff;
import com.xiaosuokeji.server.security.admin.service.intf.SecStaffService;
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
public class SecStaffServiceImpl implements SecStaffService {

    @Autowired
    private SecStaffDao secStaffDao;

    @Override
    public void save(SecStaff secStaff) throws XSBusinessException {
        SecStaff existent = new SecStaff();
        existent.setUsername(secStaff.getUsername());
        Long count = secStaffDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecStaffConsts.SEC_STAFF_EXIST);
        }
        secStaffDao.save(secStaff);
    }

    @Override
    public void remove(SecStaff secStaff) throws XSBusinessException {
        secStaffDao.remove(secStaff);
    }

    @Override
    public void update(SecStaff secStaff) throws XSBusinessException {
        if (secStaff.getName() != null) {
            SecStaff existent = new SecStaff();
            existent.setUsername(secStaff.getUsername());
            Long count = secStaffDao.count(existent);
            if (count.compareTo(0L) > 0) {
                throw new XSBusinessException(SecStaffConsts.SEC_STAFF_EXIST);
            }
        }
        secStaffDao.update(secStaff);
    }

    @Override
    public SecStaff get(SecStaff secStaff) throws XSBusinessException {
        SecStaff existent = secStaffDao.get(secStaff);
        if (existent == null) {
            throw new XSBusinessException(SecStaffConsts.SEC_STAFF_NOT_EXIST);
        }
        return secStaffDao.get(secStaff);
    }

    @Override
    public SecStaff getByUsername(SecStaff secStaff) {
        SecStaff existent = secStaffDao.getByUsername(secStaff);
        if (existent != null) {
            //获取角色列表并删除被禁用的角色
            List<SecRole> roleList = secStaffDao.listRole(secStaff);
            for (Iterator<SecRole> iterator = roleList.iterator(); iterator.hasNext();) {
                if (iterator.next().getStatus().equals(0)) {
                    iterator.remove();
                }
            }
            //获取组织列表并校验组织状态，若员工不属于任何组织或只要其所处的任意一个组织启用即可
            List<SecOrganization> organizationList = secStaffDao.listOrganization(secStaff);
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
        }
        return existent;
    }

    @Override
    public XSPageModel listAndCount(SecStaff secStaff) {
        secStaff.setDefaultSort("create_time", "DESC");
        return XSPageModel.build(secStaffDao.list(secStaff), secStaffDao.count(secStaff));
    }

    @Override
    public List<SecRole> listRole(SecStaff secStaff) {
        return secStaffDao.listRole(secStaff);
    }

    @Override
    @Transactional
    public void authorizeRole(SecStaff secStaff) throws XSBusinessException {
        SecStaff existent = secStaffDao.get(secStaff);
        if (existent == null) {
            throw new XSBusinessException(SecStaffConsts.SEC_STAFF_NOT_EXIST);
        }
        secStaffDao.removeStaffRole(existent);
        secStaffDao.saveStaffRole(secStaff);
    }
}
