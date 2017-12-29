package com.xiaosuokeji.server.controller.system;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.system.SystemConfig;
import com.xiaosuokeji.server.service.system.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统配置Controller
 * Created by xuxiaowei on 2017/11/8.
 */
@Controller
@XSLog
@XSExceptionHandler
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    //region Admin
    @RequestMapping(value = "/admin/system/systemConfig", method = RequestMethod.GET)
    public String index(Model model, SystemConfig systemConfig) {
        if(systemConfig.getPage() == null) systemConfig.setPage(1L);
        model.addAttribute("search", systemConfig);
        model.addAttribute("pageModel", systemConfigService.listAndCount(systemConfig));
        return "admin/system/systemConfig";
    }

    @RequestMapping(value = "/admin/system/systemConfig/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(SystemConfig systemConfig) throws XSBusinessException {
        return XSServiceResult.build().data(systemConfigService.get(systemConfig));
    }

    @RequestMapping(value = "/admin/system/systemConfig/getByKey", method = RequestMethod.GET)
    @ResponseBody
    public XSServiceResult adminGetByKey(SystemConfig systemConfig) throws XSBusinessException {
        return XSServiceResult.build().data(systemConfigService.getByKey(systemConfig.getKey()));
    }

    @RequestMapping(value = "/admin/system/systemConfig/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(SystemConfig.Save.class) SystemConfig systemConfig) throws XSBusinessException {
        systemConfigService.save(systemConfig);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/systemConfig/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(SystemConfig systemConfig) throws XSBusinessException {
        systemConfigService.remove(systemConfig);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/systemConfig/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(SystemConfig.Update.class) SystemConfig systemConfig) throws XSBusinessException {
        systemConfigService.update(systemConfig);
        return XSServiceResult.build();
    }
    //endregion
}
