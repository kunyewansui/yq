package com.xiaosuokeji.server.controller.system;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.system.DictData;
import com.xiaosuokeji.server.service.intf.system.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 字典数据Controller
 * Created by xuxiaowei on 2017/11/3.
 */
@Controller
@XSLog
@XSExceptionHandler
public class DictDataController {
    
    @Autowired
    private DictDataService dictDataService;

    //region Admin
    @RequestMapping(value = "/admin/system/dictData/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(DictData dictData) throws XSBusinessException {
        return XSServiceResult.build().data(dictDataService.get(dictData));
    }

    @RequestMapping(value = "/admin/system/dictData/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(DictData.Save.class) DictData dictData) throws XSBusinessException {
        dictDataService.save(dictData);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/dictData/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(DictData dictData) throws XSBusinessException {
        dictDataService.remove(dictData);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/dictData/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(DictData.Update.class) DictData dictData) throws XSBusinessException {
        dictDataService.update(dictData);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/dictData/lock/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdateLock(DictData dictData) throws XSBusinessException {
        dictDataService.updateLock(dictData);
        return XSServiceResult.build();
    }
    //endregion
}
