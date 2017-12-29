package com.xiaosuokeji.server.controller.security;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.security.SecStaffLog;
import com.xiaosuokeji.server.service.security.SecStaffLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统用户日志Controller
 * Created by xuxiaowei on 2017/11/9.
 */
@Controller
@XSLog
@XSExceptionHandler
public class SecStaffLogController {

    @Autowired
    private SecStaffLogService secStaffLogService;

    //region Admin
    @RequestMapping(value = "/admin/system/secStaffLog", method = RequestMethod.GET)
    public String indexList(Model model, SecStaffLog secStaff) {
        if(secStaff.getPage() == null) secStaff.setPage(1L);
        model.addAttribute("search", secStaff);
        model.addAttribute("pageModel", secStaffLogService.listAndCount(secStaff));
        return "admin/system/secStaffLog";
    }

    @RequestMapping(value = "/admin/security/secStaffLog/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(SecStaffLog secStaffLog) throws XSBusinessException {
        return XSServiceResult.build().data(secStaffLogService.get(secStaffLog));
    }
    //endregion
}
