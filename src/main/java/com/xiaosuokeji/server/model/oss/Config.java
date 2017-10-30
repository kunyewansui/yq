package com.xiaosuokeji.server.model.oss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by gustinlau on 10/30/17.
 */
@Service
public class Config {
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

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getDomain() {
        return domain;
    }
}
