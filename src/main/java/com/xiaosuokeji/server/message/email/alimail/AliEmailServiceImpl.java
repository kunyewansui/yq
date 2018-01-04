package com.xiaosuokeji.server.message.email.alimail;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.xiaosuokeji.server.message.email.MailService;
import com.xiaosuokeji.server.message.email.MailTemplate;
import com.xiaosuokeji.server.message.email.MailTemplateType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: guobaikun
 * Date: 2017/12/8
 * Time: 9:16
 */

@Service
public class AliEmailServiceImpl implements MailService {

    public void batchSendMail(String toAddress, String content) {
        List<String> addressList = Arrays.asList(toAddress.split(","));
        String address;
        int count = 100;//每组记录数
        for(int i=0;i< addressList.size();i+=100){
            if(i+count>addressList.size()){        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                count=addressList.size()-i;
            }
            address = StringUtils.join(addressList.subList(i,i+count),",");
            singleSendMail(address,content);
        }
    }

    public void singleSendMail(String toAddress, String content) {
        // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的"cn-hangzhou"替换为"ap-southeast-1"、或"ap-southeast-2"。
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AliMailConfig.ACCESS_KEY, AliMailConfig.ACCESS_SECRET);
        // 如果是除杭州region外的其它region（如新加坡region）， 需要做如下处理
        //try {
        //DefaultProfile.addEndpoint("dm.ap-southeast-1.aliyuncs.com", "ap-southeast-1", "Dm",  "dm.ap-southeast-1.aliyuncs.com");
        //} catch (ClientException e) {
        //e.printStackTrace();
        //}
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
            request.setAccountName(AliMailConfig.ACCOUNT_NAME);
            request.setFromAlias(AliMailConfig.FROM_ALIAS);
            request.setAddressType(1);//取值范围 0~1: 0 为随机账号；1 为发信地址。
            request.setReplyToAddress(true);//使用管理控制台中配置的回信地址
            request.setToAddress(toAddress);//目标地址，多个 email 地址可以用逗号分隔，最多100个地址。
            request.setSubject("小梭科技");
            request.setHtmlBody(content);
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        }
        catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        AliEmailServiceImpl service = new AliEmailServiceImpl();
       // send("gbk@xiaosuokeji.com", "你好呀！");
        //String content = MailTemplate.mailModel(MailTemplateType.SUPPLIER_PASS, "焜爷", "kunye", "123456");
        String content1 = MailTemplate.mailModel(MailTemplateType.SUPPLIER_UNPASS, "焜爷", "不合适");
        String content2 = MailTemplate.mailModel(MailTemplateType.ORDER_APPLY, "焜爷", "一笔发财的订单", "12000", "");
        String content3 = MailTemplate.mailModel(MailTemplateType.ORDER_APPLY_PASS, "焜爷", "一笔发财的订单");
        String content4 = MailTemplate.mailModel(MailTemplateType.ORDER_APPLY_UNPASS, "焜爷", "一笔发财的订单");
        //AliEmailUtil.send("gbk@xiaosuokeji.com", content);
        service.batchSendMail("gbk@xiaosuokeji.com", content1);
        service.batchSendMail("gbk@xiaosuokeji.com", content2);
        service.batchSendMail("gbk@xiaosuokeji.com", content3);
        service.batchSendMail("gbk@xiaosuokeji.com", content4);
    }

}
