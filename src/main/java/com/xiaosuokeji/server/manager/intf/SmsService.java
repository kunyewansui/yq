package com.xiaosuokeji.server.manager.intf;

import java.util.Map;

/**
 * 短信Service<br/>
 * Created by xuxiaowei on 2017/8/1.
 */
public interface SmsService {

    /**
     * 发送短信
     *
     * @param mobile   手机号
     * @param template 短信模板
     * @param params   模板变量
     * @throws Exception 验证码发送失败
     */
    void sendBySms(String mobile, String template, Map<String, String> params) throws Exception;

    /**
     * 发送语音短信
     *
     * @param mobile   手机号
     * @param template 短信模板
     * @param params   模板变量
     * @throws Exception 验证码发送失败
     */
    void sendByTts(String mobile, String template, Map<String, String> params) throws Exception;
}
