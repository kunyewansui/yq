package com.xiaosuokeji.server.controller.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.service.security.SecResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统资源Controller
 * Created by xuxiaowei on 2017/11/3.
 */
@Controller
@XSLog
@XSExceptionHandler
public class SecResourceController {
    
    @Autowired
    private SecResourceService secResourceService;

    //region Admin
    @RequestMapping(value = "/admin/system/secResource", method = RequestMethod.GET)
    public String indexList(Model model, SecResource secResource) throws JsonProcessingException {
        if(secResource.getPage() == null) secResource.setPage(1L);
        model.addAttribute("search", secResource);
        model.addAttribute("pageModel", secResourceService.listAndCount(secResource));
        model.addAttribute("resourceTree", XSJackson.toJsonString(secResourceService.tree(new SecResource())));
        return "admin/system/secResource";
    }

    @RequestMapping(value = "/admin/security/secResource/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(SecResource secResource) throws XSBusinessException {
        return XSServiceResult.build().data(secResourceService.get(secResource));
    }

    @RequestMapping(value = "/admin/security/secResource/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(SecResource.Save.class) SecResource secResource) throws XSBusinessException {
        secResourceService.save(secResource);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secResource/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(SecResource secResource) throws XSBusinessException {
        secResourceService.remove(secResource);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/security/secResource/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(SecResource.Update.class) SecResource secResource) throws XSBusinessException {
        secResourceService.update(secResource);
        return XSServiceResult.build();
    }
    //endregion
}
