package com.xiaosuokeji.server.controller.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.security.SecOrganization;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.service.intf.security.SecStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户Controller
 * Created by xuxiaowei on 2017/11/6.
 */
@Controller
@XSLog
@XSExceptionHandler
public class SecStaffController {

    @Autowired
    private SecStaffService secStaffService;

    //region Admin
    @RequestMapping(value = "/admin/system/secStaff", method = RequestMethod.GET)
    public String indexList(Model model, SecStaff secStaff) throws JsonProcessingException {
        if(secStaff.getPage() == null) secStaff.setPage(1L);
        model.addAttribute("search", secStaff);
        model.addAttribute("pageModel", secStaffService.listAndCount(secStaff));
        return "admin/system/secStaff";
    }

    @RequestMapping(value = "/admin/security/secStaff/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(SecStaff secStaff, HttpServletRequest request) throws XSBusinessException {
        //防止非超级管理员获取超级管理员信息
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        SecStaff currentStaff = (SecStaff) securityContextImpl.getAuthentication().getPrincipal();
        if (!currentStaff.getId().equals(1L)) {
            secStaff.setSuperior(0);
        }
        return XSServiceResult.build().data(secStaffService.get(secStaff));
    }

    @RequestMapping(value = "/admin/security/secStaff/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(SecStaff.Save.class) SecStaff secStaff) throws Exception {
        secStaffService.save(secStaff);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secStaff/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(SecStaff secStaff) throws XSBusinessException {
        secStaffService.remove(secStaff);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secStaff/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(SecStaff.Update.class) SecStaff secStaff, HttpServletRequest request) throws Exception {
        //防止非超级管理员修改超级管理员信息
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        SecStaff currentStaff = (SecStaff) securityContextImpl.getAuthentication().getPrincipal();
        if (!currentStaff.getId().equals(1L)) {
            secStaff.setSuperior(0);
        }
        secStaffService.update(secStaff);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secStaff/secRole/list", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminListRole(SecStaff secStaff) throws XSBusinessException {
        return XSServiceResult.build().data(secStaffService.listRole(secStaff));
    }

    @RequestMapping(value = "/admin/security/secStaff/secRole/authorize", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminAuthorizeRole(SecStaff secStaff, Long[] roleIds) throws XSBusinessException {
        List<SecRole> roleList = new ArrayList<>();
        for (Long item : roleIds) {
            roleList.add(new SecRole(item));
        }
        secStaff.setRoleList(roleList);
        secStaffService.authorizeRole(secStaff);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secStaff/secOrg/tree", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminListOrg(SecStaff secStaff) throws XSBusinessException {
        return XSServiceResult.build().data(secStaffService.treeOrganization(secStaff));
    }

    @RequestMapping(value = "/admin/security/secStaff/secOrg/authorize", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminAuthorizeOrg(SecStaff secStaff, Long[] organizationIds) throws XSBusinessException {
        List<SecOrganization> orgList = new ArrayList<>();
        for (Long item : organizationIds) {
            orgList.add(new SecOrganization(item));
        }
        secStaff.setOrganizationList(orgList);
        secStaffService.authorizeOrganization(secStaff);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secStaff/info/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGetInfo(SecStaff secStaff, HttpServletRequest request) throws XSBusinessException {
        //防止非超级管理员获取超级管理员信息
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        SecStaff currentStaff = (SecStaff) securityContextImpl.getAuthentication().getPrincipal();
        if (!currentStaff.getId().equals(1L)) {
            secStaff.setSuperior(0);
        }
        return XSServiceResult.build().data(secStaffService.get(secStaff));
    }

    @RequestMapping(value = "/admin/security/secStaff/info/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdateInfo(SecStaff secStaff, HttpServletRequest request) throws Exception {
        //防止非超级管理员修改超级管理员信息
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        SecStaff currentStaff = (SecStaff) securityContextImpl.getAuthentication().getPrincipal();
        if (!currentStaff.getId().equals(1L)) {
            secStaff.setSuperior(0);
        }
        secStaff.setId(currentStaff.getId());
        secStaffService.update(secStaff);
        return XSServiceResult.build();
    }
    //endregion
}
