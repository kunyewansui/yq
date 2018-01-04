package com.xiaosuokeji.server.message.email;

/**
 * Created with IntelliJ IDEA.
 * User: guobaikun
 * Date: 2018/1/4
 * Time: 11:32
 */

public interface MailService {

    /**
     * 批量发送邮件
     * @param toAddress 收信地址，以逗号分隔
     * @param content
     */
    void batchSendMail(String toAddress, String content);

    /**
     * 发送邮件
     * @param toAddress
     * @param content
     */
    void singleSendMail(String toAddress, String content);
}
