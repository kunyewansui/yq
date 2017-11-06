package com.xiaosuokeji.server.controller.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.security.SecOrganization;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.service.intf.security.SecOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统组织Controller
 * Created by xuxiaowei on 2017/11/5.
 */
@Controller
@XSLog
@XSExceptionHandler
public class SecOrganizationController {

    @Autowired
    private SecOrganizationService secOrganizationService;

    //region Admin
    @RequestMapping(value = "/admin/system/secOrganization", method = RequestMethod.GET)
    public String indexList(Model model, SecOrganization secOrganization) throws JsonProcessingException {
        if(secOrganization.getPage() == null) secOrganization.setPage(1L);
        model.addAttribute("search", secOrganization);
        model.addAttribute("pageModel", secOrganizationService.listAndCount(secOrganization));
        model.addAttribute("orgTree", XSJackson.toJsonString(secOrganizationService.tree(new SecOrganization())));
        return "admin/system/secOrganization";
    }

    @RequestMapping(value = "/admin/security/secOrganization/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(SecOrganization secOrganization) throws XSBusinessException {
        return XSServiceResult.build().data(secOrganizationService.get(secOrganization));
    }

    @RequestMapping(value = "/admin/security/secOrganization/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(SecOrganization.Save.class) SecOrganization secOrganization) throws XSBusinessException {
        secOrganizationService.save(secOrganization);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secOrganization/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(SecOrganization secOrganization) throws XSBusinessException {
        secOrganizationService.remove(secOrganization);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secOrganization/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(SecOrganization.Update.class) SecOrganization secOrganization) throws XSBusinessException {
        secOrganizationService.update(secOrganization);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secOrganization/secRole/list", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminListRole(SecOrganization secOrganization) throws XSBusinessException {
        return XSServiceResult.build().data(secOrganizationService.listRole(secOrganization));
    }

    @RequestMapping(value = "/admin/security/secOrganization/authorize", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminAuthorize(SecOrganization secOrganization, Long[] roleIds) throws XSBusinessException {
        List<SecRole> roleList = new ArrayList<>();
        for (Long item : roleIds) {
            roleList.add(new SecRole(item));
        }
        secOrganization.setRoleList(roleList);
        secOrganizationService.authorizeRole(secOrganization);
        return XSServiceResult.build();
    }
    //endregion
}
