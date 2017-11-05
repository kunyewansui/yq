package com.xiaosuokeji.server.controller.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.service.intf.security.SecRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色Controller
 * Created by xuxiaowei on 2017/11/5.
 */
@Controller
@XSLog
@XSExceptionHandler
public class SecRoleController {

    @Autowired
    private SecRoleService secRoleService;

    //region Admin
    @RequestMapping(value = "/admin/system/secRole", method = RequestMethod.GET)
    public String indexList(Model model, SecRole secRole) throws Exception {
        if(secRole.getPage() == null) secRole.setPage(1L);
        model.addAttribute("search", secRole);
        model.addAttribute("pageModel", secRoleService.listAndCount(secRole));
        return "admin/system/secRole";
    }

    @RequestMapping(value = "/admin/security/secRole/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(SecRole secRole) throws XSBusinessException {
        return XSServiceResult.build().data(secRoleService.get(secRole));
    }

    @RequestMapping(value = "/admin/security/secRole/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(SecRole.Save.class) SecRole secRole) throws XSBusinessException {
        secRoleService.save(secRole);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secRole/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(SecRole secRole) throws XSBusinessException {
        secRoleService.remove(secRole);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secRole/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(SecRole.Update.class) SecRole secRole) throws XSBusinessException {
        secRoleService.update(secRole);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secRole/tree", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminTree(SecRole secRole) throws XSBusinessException {
        SecStaff secStaff = new SecStaff();
        secStaff.setId(1L);
        return XSServiceResult.build().data(secRoleService.treeResource(secRole, secStaff));
    }

    @RequestMapping(value = "/admin/security/secRole/authorize", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminAuthorize(SecRole secRole, Long[] resourceIds) throws XSBusinessException {
        List<SecResource> resourceList = new ArrayList<>();
        for (Long item : resourceIds) {
            resourceList.add(new SecResource(item));
        }
        secRole.setResourceList(resourceList);
        SecStaff secStaff = new SecStaff();
        secStaff.setId(1L);
        secRoleService.authorizeResource(secRole, secStaff);
        return XSServiceResult.build();
    }
    //endregion
}
