package com.xiaosuokeji.server.controller.common;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.manager.oss.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by gustinlau on 10/30/17.
 */
@Controller
@XSLog
@XSExceptionHandler
public class CommonController {

    @Autowired
    OssService ossService;

    /**
     * UEditor 入口
     *
     * @return
     */
    @RequestMapping("/admin/ueditor/controller")
    public String controller() {
        return "admin/common/_ueditor";
    }


    /**
     * 文件上传
     *
     * @param folders
     * @param files
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/common/api/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult fileUpload(String[] folders, MultipartFile[] files) throws Exception {
        List resultList = ossService.imageUpload(folders, files);
        if (resultList == null) {
            throw new Exception();
        }
        return XSServiceResult.build().data(resultList);
    }

    /**
     * 文件上传（兼容IE）
     * @param folders
     * @param files
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/admin/common/api/file/upload2", method = RequestMethod.POST ,produces="text/html;charset=utf-8")
    @ResponseBody
    public String fileUpload2(@RequestParam String[] folders, @RequestParam MultipartFile[] files) throws Exception {
        List resultList = ossService.imageUpload(folders, files);
        if (resultList == null) {
            throw new Exception();
        }
        return XSJackson.toJsonString(XSServiceResult.build().data(resultList));
    }
}
