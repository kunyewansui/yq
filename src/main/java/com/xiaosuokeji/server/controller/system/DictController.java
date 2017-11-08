package com.xiaosuokeji.server.controller.system;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.annotation.SaveURL;
import com.xiaosuokeji.server.model.system.Dict;
import com.xiaosuokeji.server.model.system.DictData;
import com.xiaosuokeji.server.service.intf.system.DictDataService;
import com.xiaosuokeji.server.service.intf.system.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 字典Controller
 * Created by xuxiaowei on 2017/11/1.
 */
@Controller
@XSLog
@XSExceptionHandler
public class DictController {

    @Autowired
    private DictService dictService;

    @Autowired
    private DictDataService dictDataService;

    //region Admin
    @RequestMapping(value = "/admin/system/dict", method = RequestMethod.GET)
    @SaveURL
    public String index(Model model, Dict dict) {
        if(dict.getPage() == null) dict.setPage(1L);
        model.addAttribute("search", dict);
        model.addAttribute("pageModel", dictService.listAndCount(dict));
        return "admin/system/dict";
    }

    @RequestMapping(value = "/admin/system/dict/get", method = RequestMethod.GET)
    public String adminGet(Model model, HttpServletRequest request, Dict dict, String backUrl) throws XSBusinessException {
        model.addAttribute("backUrl", backUrl);
        try {
            model.addAttribute("dict", dictService.get(dict));
        } catch (XSBusinessException e) {
            model.addAttribute("dict", null);
        }
        DictData dictData = new DictData();
        dictData.setDict(dict);
        model.addAttribute("dictDataList", dictDataService.list(dictData));
        return "admin/system/dictGet";
    }

    @RequestMapping(value = "/admin/system/dict/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(Dict.Save.class) Dict dict) throws XSBusinessException {
        dictService.save(dict);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/dict/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(Dict dict) throws XSBusinessException {
        dictService.remove(dict);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/dict/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(Dict.Update.class) Dict dict) throws XSBusinessException {
        dictService.update(dict);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/dict/lock/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdateLock(Dict dict) throws XSBusinessException {
        dictService.updateLock(dict);
        return XSServiceResult.build();
    }
    //endregion
}
