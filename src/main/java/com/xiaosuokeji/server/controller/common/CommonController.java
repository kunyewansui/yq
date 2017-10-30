package com.xiaosuokeji.server.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by gustinlau on 10/30/17.
 */
public class CommonController {
        /**
     * 文件上传
     *
     * @param folders
     * @param files
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(String[] folders, MultipartFile[] files, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        return GsonUtils.toJson(OssUtils.fileUpload(folders, files));
    }
}
