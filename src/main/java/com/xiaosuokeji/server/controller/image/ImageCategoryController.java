package com.xiaosuokeji.server.controller.image;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.annotation.XSPagination;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.image.ImageCategory;
import com.xiaosuokeji.server.service.image.intf.ImageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 图片分类Controller
 * Created by xuxiaowei on 2017/10/23.
 */
@Controller
@XSLog
@XSExceptionHandler
public class ImageCategoryController {

    @Autowired
    private ImageCategoryService imageCategoryService;

    @RequestMapping(value = "/app/v1/image/category/insert", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appSave(@Validated(ImageCategory.Save.class) @RequestBody ImageCategory imageCategory)
            throws XSBusinessException {
        imageCategoryService.save(imageCategory);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/app/v1/image/category/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appRemove(@RequestBody ImageCategory imageCategory) throws XSBusinessException {
        imageCategoryService.remove(imageCategory);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/app/v1/image/category/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appUpdate(@Validated(ImageCategory.Update.class) @RequestBody ImageCategory imageCategory)
            throws XSBusinessException {
        imageCategoryService.update(imageCategory);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/app/v1/image/category/lock/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appUpdateLock(@RequestBody ImageCategory imageCategory) throws XSBusinessException {
        imageCategoryService.updateLock(imageCategory);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/app/v1/image/category/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appGet(@RequestBody ImageCategory imageCategory) throws XSBusinessException {
        return XSServiceResult.build().data(imageCategoryService.get(imageCategory));
    }

    @RequestMapping(value = "/app/v1/image/category/tree", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appTree(@RequestBody ImageCategory imageCategory) {
        return XSServiceResult.build().data(imageCategoryService.tree(imageCategory));
    }

}
