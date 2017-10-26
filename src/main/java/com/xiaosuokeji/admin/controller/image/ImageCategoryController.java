package com.xiaosuokeji.admin.controller.image;

import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.server.service.image.intf.ImageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gustinlau on 26/10/2017.
 */
@Controller("adminImageCategoryController")
@XSLog
public class ImageCategoryController {

    @Autowired
    private ImageCategoryService imageCategoryService;


    @RequestMapping("/image/manage")
    public String manage(){
        return "admin/content/imageCategory";
    }

}
