package com.xiaosuokeji.server.controller.system;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.system.Version;
import com.xiaosuokeji.server.service.intf.system.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by gustinlau on 11/1/17.
 */
@Controller
@XSLog
@XSExceptionHandler
public class VersionController {

    @Autowired
    private VersionService versionService;

    @RequestMapping(value = "/admin/system/version", method = RequestMethod.GET)
    public String index(Model model, Version version) {
        model.addAttribute("search", version);
        model.addAttribute("pageModel", versionService.listAndCount(version));
        return "admin/system/version";
    }

    @RequestMapping(value = "/admin/system/version/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(Version version) throws XSBusinessException {
        return XSServiceResult.build().data(versionService.get(version));
    }

    @RequestMapping(value = "/admin/system/version/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(Version.Save.class) Version version) throws XSBusinessException {
        versionService.save(version);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/version/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(Version version) throws XSBusinessException {
        versionService.remove(version);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/version/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(Version.Update.class) Version version) throws XSBusinessException {
        versionService.update(version);
        return XSServiceResult.build();
    }
}
