package com.xiaosuokeji.server.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.system.Country;
import com.xiaosuokeji.server.service.system.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

/**
 * 国家Controller
 * Created by xuxiaowei on 2018/5/3.
 */
@Controller
@XSLog
@XSExceptionHandler
public class CountryController {

    @Autowired
    private CountryService countryService;

    @RequestMapping(value = "/admin/system/country", method = RequestMethod.GET)
    public String index(Model model, Country country) {
        if(country.getPage() == null) country.setPage(1L);
        model.addAttribute("search", country);
        model.addAttribute("pageModel", countryService.listAndCount(country));
        return "admin/system/country";
    }

    @RequestMapping(value = "/admin/system/country/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(Country country) throws XSBusinessException {
        return XSServiceResult.build().data(countryService.get(country));
    }

    @RequestMapping(value = "/admin/system/country/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(Country.Save.class) Country country) throws XSBusinessException {
        countryService.save(country);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/country/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(Country country) throws XSBusinessException {
        countryService.remove(country);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/country/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(Country.Update.class) Country country) throws XSBusinessException {
        countryService.update(country);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/system/country/status/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdateStatus(Country country) {
        countryService.updateStatus(country);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/app/system/country/list", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appList(Country country) {
        country.setStatus(1);
        return XSServiceResult.build().data(countryService.listCombo(country));
    }

    @RequestMapping(value = "/admin/system/country/import", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult importTemplate(@RequestParam MultipartFile myfile) throws Exception {
        countryService.importTemplate(myfile);
        return XSServiceResult.build();
    }
}
