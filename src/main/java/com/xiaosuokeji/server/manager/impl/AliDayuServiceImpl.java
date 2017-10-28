package com.xiaosuokeji.server.manager.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSStatusPair;
import com.xiaosuokeji.server.manager.intf.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 阿里大于短信ServiceImpl<br/>
 * 阿里大于流控规则<br/>
 * 使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时，10条/天<br/>
 * 一个手机号码通过阿里大于平台只能收到40条/天<br/>
 * Created by xuxiaowei on 2017/8/1.
 */
public class AliDayuServiceImpl implements SmsService {

    public static final XSStatusPair SMS_ERROR = XSStatusPair.build(1006, "验证码发送失败{}");
    private static final Logger logger = LoggerFactory.getLogger(AliDayuServiceImpl.class);
    @Value("${daYu.appKey}")
    private String appKey;

    @Value("${daYu.appSecret}")
    private String appSecret;

    @Value("${daYu.url}")
    private String url;

    @Value("${daYu.signature}")
    private String signature;

    @Value("${dayu.showNum}")
    private String showNum;

    private TaobaoClient client = null;

    @PostConstruct
    private void init() {
        client = new DefaultTaobaoClient(url, appKey, appSecret);
    }

    @Override
    public void sendBySms(String mobile, String template, Map<String, String> params) throws Exception {
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName(signature);
        req.setSmsParamString(XSJackson.toJsonString(params));
        req.setRecNum(mobile);
        req.setSmsTemplateCode(template);
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        String result = rsp.getBody();
        JsonNode resultNode = XSJackson.toJsonNode(rsp.getBody());
        if (resultNode.get("error_response") != null) {
            logger.error("阿里大于短信发送失败，失败原因：" + result);
            String errorMsg = "";
            if (resultNode.get("error_response").get("sub_code").textValue().equals("isv.BUSINESS_LIMIT_CONTROL"))
                errorMsg = "，请求过于频繁，稍后重试";
            throw new XSBusinessException(SMS_ERROR.get(errorMsg));
        }
    }

    @Override
    public void sendByTts(String mobile, String template, Map<String, String> params) throws Exception {
        AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
        req.setTtsParamString(XSJackson.toJsonString(params));
        req.setCalledNum(mobile);
        req.setCalledShowNum(showNum);
        req.setTtsCode(template);
        AlibabaAliqinFcTtsNumSinglecallResponse rsp = client.execute(req);
        String result = rsp.getBody();
        JsonNode resultNode = XSJackson.toJsonNode(rsp.getBody());
        if (resultNode.get("error_response") != null) {
            logger.error("ali dayu tts error msg - " + result);
            String errorMsg = "";
            if (resultNode.get("error_response").get("sub_code").textValue().equals("isv.BUSINESS_LIMIT_CONTROL"))
                errorMsg = "，请求过于频繁，稍后重试";
            throw new XSBusinessException(SMS_ERROR.get(errorMsg));
        }
    }
}
