package com.xiaosuokeji.admin.controller.image;

import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.image.ImageCategory;
import com.xiaosuokeji.server.service.image.intf.ImageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gustinlau on 26/10/2017.
 */
@Controller("adminImageCategoryController")
@XSLog
public class ImageCategoryController {

    @Autowired
    private ImageCategoryService imageCategoryService;


    @RequestMapping(value = "/image/category", method = RequestMethod.GET)
    public String manage() {
        return "admin/content/imageCategory";
    }

    @RequestMapping(value = "/image/category/tree", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult imageCategoryTree(ImageCategory imageCategory) {
        imageCategory = imageCategory == null ? new ImageCategory() : imageCategory;
        return XSServiceResult.build().data(imageCategoryService.tree(imageCategory));
    }

    @RequestMapping(value = "/image/category/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult imageCategoryGet(ImageCategory imageCategory) throws XSBusinessException {
        return XSServiceResult.build().data(imageCategoryService.get(imageCategory));
    }


    @RequestMapping(value = "/image/category/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult imageCategorySave(@Validated(ImageCategory.Save.class) ImageCategory imageCategory) throws XSBusinessException {
        imageCategoryService.save(imageCategory);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/image/category/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult imageCategoryRemove( ImageCategory imageCategory) throws XSBusinessException {
        imageCategoryService.remove(imageCategory);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/image/category/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult imageCategoryUpdate(@Validated(ImageCategory.Update.class) ImageCategory imageCategory)
            throws XSBusinessException {
        imageCategoryService.update(imageCategory);
        return XSServiceResult.build();
    }


}
