package com.xiaosuokeji.server.controller.image;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.image.Image;
import com.xiaosuokeji.server.model.image.ImageCategory;
import com.xiaosuokeji.server.service.intf.image.ImageCategoryService;
import com.xiaosuokeji.server.service.intf.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 图片Controller
 * Created by xuxiaowei on 2017/10/26.
 */
@Controller
@XSLog
@XSExceptionHandler
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageCategoryService imageCategoryService;

    //region Admin
    @RequestMapping(value = "/admin/content/image", method = RequestMethod.GET)
    public String index(Model model, Image image) throws Exception {
        if(image.getPage() == null) image.setPage(1L);
        model.addAttribute("search", image);
        model.addAttribute("pageModel", imageService.listAndCount(image));
        model.addAttribute("categoryTree", XSJackson.toJsonString(imageCategoryService.tree(new ImageCategory())));
        return "admin/content/image";
    }

    @RequestMapping(value = "/admin/content/image/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(Image image) throws XSBusinessException {
        return XSServiceResult.build().data(imageService.get(image));
    }

    @RequestMapping(value = "/admin/content/image/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(Image.Save.class) Image image) throws XSBusinessException {
        imageService.save(image);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/content/image/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(Image image) throws XSBusinessException {
        imageService.remove(image);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/content/image/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(Image.Update.class) Image image) throws XSBusinessException {
        imageService.update(image);
        return XSServiceResult.build();
    }
    //endregion
}
