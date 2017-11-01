package com.xiaosuokeji.server.controller.content;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.image.Image;
import com.xiaosuokeji.server.service.intf.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "/app/v1/image/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appSave(@Validated(Image.Save.class) @RequestBody Image image) {
        imageService.save(image);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/app/v1/image/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appRemove(@RequestBody Image image) {
        imageService.remove(image);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/app/v1/image/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appUpdate(@Validated(Image.Update.class) @RequestBody Image image) {
        imageService.update(image);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/app/v1/image/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appGet(@RequestBody Image image) throws XSBusinessException {
        return XSServiceResult.build().data(imageService.get(image));
    }

    @RequestMapping(value = "/app/v1/image/list", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appListAndCount(@RequestBody Image image) {
        return XSServiceResult.build().data(imageService.listAndCount(image));
    }

    @RequestMapping(value = "/app/v1/image/listCombo", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult appListCombo(@RequestBody Image image) {
        return XSServiceResult.build().data(imageService.listCombo(image));
    }
}
