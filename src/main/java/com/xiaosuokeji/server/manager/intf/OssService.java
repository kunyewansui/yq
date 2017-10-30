package com.xiaosuokeji.server.manager.intf;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by gustinlau on 10/30/17.
 */
public interface OssService {
    /**
     * 图片上传
     * @param folders   文件夹名称数组
     * @param files     文件数组
     * @param withSize  返回URL是否带图片信息
     * @return
     * @throws Exception
     */
    List<String>  imageUpload(String folders[], MultipartFile[] files, Boolean withSize);

    List<String>  imageUpload(String folders[], MultipartFile[] files);

    List<String>  fileUpload(String folders[], MultipartFile[] files);

    List<String> fileUpload(String folder, MultipartFile[] files);

    void fileDownload(String fileName, OutputStream out);
}
