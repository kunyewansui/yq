package com.xiaosuokeji.server.controller.common;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.manager.intf.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
}
