package com.xiaosuokeji.server.controller.image;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.image.ImageCategory;
import com.xiaosuokeji.server.service.image.intf.ImageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 图片分类Controller<br/>
 * Created by xuxiaowei on 2017/10/23.
 */
@Controller
@XSLog
@XSExceptionHandler
public class ImageCategoryController {

    @Autowired
    private ImageCategoryService imageCategoryService;

    @RequestMapping(value = "/app/image/category/listCombo", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appListCombo(@RequestBody ImageCategory imageCategory) {
        return XSServiceResult.build().data(imageCategoryService.listCombo(imageCategory));
    }
}
