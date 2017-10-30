package com.xiaosuokeji.server.manager.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.xiaosuokeji.framework.http.XSHttpClientService;
import com.xiaosuokeji.server.manager.intf.OssService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gustinlau on 10/30/17.
 */
@Service("ossService")
@Order()
public class OssServiceImpl implements OssService {

    private static final Logger logger = LoggerFactory.getLogger(OssServiceImpl.class);

    @Autowired
    private XSHttpClientService xsHttpClientService;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.endPoint}")
    private String endPoint;
    @Value("${oss.domain}")
    private String domain;


    @Override
    public List<String> imageUpload(String[] folders, MultipartFile[] files, Boolean withSize) {
        OSSClient client = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        ArrayList<String> list;
        try {
            int sum = folders.length;
            InputStream inputStream;
            list = new ArrayList<>();
            for (int i = 0; i < sum; i++) {
                String newName = rename(files[i].getOriginalFilename());//新文件名
                inputStream = files[i].getInputStream();
                client.putObject(bucketName, folders[i] + "/" + newName, inputStream);
                String url = domain + "/" + folders[i] + "/" + newName;
                if (withSize) {
                    //获取图片的高和宽并且拼接在图片的url
                    JSONObject info = new JSONObject(xsHttpClientService.makeHttpRequest(url + "?x-oss-process=image/info", "GET").getEntity().getContent());
                    url += "?x-oss-process=image/resize,w_" + info.getJSONObject("ImageWidth").getString("value") + ",h_" +
                            info.getJSONObject("ImageHeight").getString("value");
                }
                list.add(url);
            }
        } catch (Exception e) {
            logger.error("error:", e);
            list = null;
        } finally {
            client.shutdown();
        }
        return list;
    }

    @Override
    public List<String> imageUpload(String[] folders, MultipartFile[] files) {
        return imageUpload(folders, files, false);
    }

    @Override
    public List<String> fileUpload(String[] folders, MultipartFile[] files) {
        return imageUpload(folders, files, false);
    }


    @Override
    public List<String> fileUpload(String folder, MultipartFile[] files) {
        OSSClient client = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        ArrayList<String> list;
        try {
            int sum = files.length;
            InputStream inputStream;
            list = new ArrayList<>();
            for (int i = 0; i < sum; i++) {
                String newName = rename(files[i].getOriginalFilename());//新文件名
                inputStream = files[i].getInputStream();
                client.putObject(bucketName, folder + "/" + newName, inputStream);
                String url = domain + "/" + folder + "/" + newName;
                list.add(url);
            }
        } catch (Exception e) {
            logger.error("error:", e);
            list = null;
        } finally {
            client.shutdown();
        }
        return list;
    }

    @Override
    public void fileDownload(String fileName, OutputStream out) throws IOException {
        OSSClient client = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        InputStream in = null;
        try {
            OSSObject ossObject = client.getObject(bucketName, fileName);
            in = ossObject.getObjectContent();
            byte[] buf = new byte[1024];
            int size;
            while ((size = in.read(buf, 0, buf.length)) != -1) {
                out.write(buf, 0, size);
            }
            in.close();
        } finally {
            client.shutdown();
            if (in !=null) in.close();
        }
    }


    /**
     * 文件重命名
     *
     * @param originalFilename 文件原始名称
     * @return 重命名后文件名
     */
    private static String rename(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");       //.的位置
        String suffix = originalFilename.substring(index, originalFilename.length());//文件后缀
        Date date = new Date();
        String name_1 = new SimpleDateFormat("yyyyMMddhhmmss").format(date);
        String name_2 = String.valueOf(date.getTime());
        return name_1 + name_2 + suffix;            //新文件名
    }

}
