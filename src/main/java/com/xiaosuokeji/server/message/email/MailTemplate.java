package com.xiaosuokeji.server.message.email;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: guobaikun
 * Date: 2017/12/8
 * Time: 10:28
 */

public class MailTemplate {

    /**
     * 供应商申请通过模板
     * @param contacts 联系人
     * @param username 账号
     * @param password 密码
     * @return
     */
    private static String supplierAuditPassModel(String contacts, String username,String password) {
        return "<p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">尊敬的</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span><span style=\"font-size: 18pt; font-family: DengXian;\">"+contacts+"</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span><span style=\"font-size: 18pt; font-family: DengXian;\">：</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">您的供应商申请已通过，系统已自动为您分配账号：</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">账号：</span><span style=\"font-size: 18pt; font-family: DengXian;\">"+username+"</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">密码：</span><span style=\"font-size: 18pt; font-family: DengXian;\">"+password+"</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">登录地址为：</span><span style=\"font-size: 18pt; font-family: DengXian;\">http://</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">supplier.otom-manufaction.com</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">首次登录后，请修改密码，以保障您的账户安全！</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">一到一百万制造（广州）有限公司</span></p><p><br/></p>";
    }

    /**
     * 供应商申请不通过模板
     * @param contacts 联系人
     * @param reason 拒绝原因
     * @return
     */
    private static String supplierAuditUnpassModel(String contacts, String reason) {
        return "<p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">尊敬的</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span><span style=\"font-size: 18pt; font-family: DengXian;\">"+contacts+"</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span><span style=\"font-size: 18pt; font-family: DengXian;\">：</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">您的供应商申请</span><span style=\"font-size: 18pt; font-family: DengXian;\">已被驳回，驳回原因为：</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span><span style=\"font-size: 18pt; font-family: DengXian;\">"+reason+"</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">资料填写不完整</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">请修改好资料后，重新提交申请！</span><span style=\"font-size: 18pt; font-family: DengXian;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">一</span><span style=\"font-size: 18pt; font-family: DengXian;\">到一百万制造（广州）有限公司</span></p><p><br/></p>";
    }

    /**
     * 订单报名通知模板
     * @param contacts 联系人
     * @param name 订单名称
     * @param amount 订单金额
     * @param descn 订单描述
     * @return
     */
    private static String orderApplyModel(String contacts, String name, String amount, String descn) {
        String content;
        if(!StringUtils.isBlank(descn)){
            content = "<p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">尊敬的</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span><span style=\"font-size: 18pt; font-family: DengXian;\">"+contacts+"</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span><span style=\"font-size: 18pt; font-family: DengXian;\">：</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">现有订单“</span><span style=\"font-size: 18pt; font-family: DengXian;\">【</span><span style=\"font-size: 18pt; font-family: DengXian;\">"+name+"</span><span style=\"font-size: 18pt; font-family: DengXian;\">】</span><span style=\"font-size: 18pt; font-family: DengXian;\">”，</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">订单金额：</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\"></span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">"+amount+"￥</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\"></span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">，具体</span><span style=\"font-size: 18pt; font-family: DengXian;\">需求如下：</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\"></span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">"+descn+"</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\"></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">若贵司有意向参与，请登录我司供应商系统进行报名。</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">供应商系统地址为：</span><span style=\"font-size: 18pt; font-family: DengXian;\">http://</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">supplier.otom-manufaction.com</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">一</span><span style=\"font-size: 18pt; font-family: DengXian;\">到一百万制造（广州）有限公司</span></p><p><br/></p>";
        }else{
            content = "<p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">尊敬的</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span><span style=\"font-size: 18pt; font-family: DengXian;\">"+contacts+"</span><span style=\"font-size: 18pt; font-family: DengXian;\"></span><span style=\"font-size: 18pt; font-family: DengXian;\">：</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">现有订单“</span><span style=\"font-size: 18pt; font-family: DengXian;\">【</span><span style=\"font-size: 18pt; font-family: DengXian;\">"+name+"</span><span style=\"font-size: 18pt; font-family: DengXian;\">】</span><span style=\"font-size: 18pt; font-family: DengXian;\">”，</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">订单金额：</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\"></span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">"+amount+"￥，</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\"></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">若贵司有意向参与，请登录我司供应商系统进行报名。</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">供应商系统地址为：</span><span style=\"font-size: 18pt; font-family: DengXian;\">http://</span><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">supplier.otom-manufaction.com</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">一</span><span style=\"font-size: 18pt; font-family: DengXian;\">到一百万制造（广州）有限公司</span></p><p><br/></p>";
        }
        return content;
    }

    /**
     * 订单采纳模板
     * @param contacts 联系人
     * @param name 订单名称
     * @return
     */
    private static String orderApplyPassModel(String contacts, String name) {
        return "<p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">尊敬的</span><span style=\"font-size: 18pt; font-family: DengXian;\">"+contacts+"</span><span style=\"font-size: 18pt; font-family: DengXian;\">：</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">已收到您关于订单【</span><span style=\"font-size: 18pt; font-family: DengXian;\">"+name+"</span><span style=\"font-size: 18pt; font-family: DengXian;\">】的报名信息，经我司评估，已选定贵司作为我司的合作伙伴，后续将会有专人联系您进行细节的磋商。</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">请您持续留意订单的发布信息，望后续依旧能达成业务合作。</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">一</span><span style=\"font-size: 18pt; font-family: DengXian;\">到一百万制造（广州）有限公司</span></p><p><br/></p>";
    }

    /**
     * 订单拒绝模板
     * @param contacts 联系人
     * @param name 订单名称
     * @return
     */
    private static String orderApplyUnpassModel(String contacts, String name) {
        return "<p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">尊敬的</span><span style=\"font-size: 18pt; font-family: DengXian;\">"+contacts+"</span><span style=\"font-size: 18pt; font-family: DengXian;\">：</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\"><br/></span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">已收到您关于订单【</span><span style=\"font-size: 18pt; font-family: DengXian;\">"+name+"</span><span style=\"font-size: 18pt; font-family: DengXian;\">】的报名信息，经我司评估，已选定更合适的供应商进行合作，</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">感谢您对我司的支持。</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian; font-weight: bold;\">请您持续留意订单的发布信息，望下次能达成合作。</span></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><br/></p><p style=\"margin-top: 0pt; margin-bottom: 0pt; margin-left: 0in; direction: ltr; unicode-bidi: embed; word-break: normal;\"><span style=\"font-size: 18pt; font-family: DengXian;\">一</span><span style=\"font-size: 18pt; font-family: DengXian;\">到一百万制造（广州）有限公司</span></p><p><br/></p>";
    }


    public static String mailModel(MailTemplateType template,String... params) {
        String model;
        switch (template){
            case SUPPLIER_PASS:
                model = supplierAuditPassModel(params[0],params[1],params[2]);
                break;
            case SUPPLIER_UNPASS:
                model = supplierAuditUnpassModel(params[0],params[1]);
                break;
            case ORDER_APPLY:
                model = orderApplyModel(params[0],params[1],params[2],params[3]);
                break;
            case ORDER_APPLY_PASS:
                model = orderApplyPassModel(params[0],params[1]);
                break;
            case ORDER_APPLY_UNPASS:
                model = orderApplyUnpassModel(params[0],params[1]);
                break;
            default:model = null;
                break;
        }
        return model;
    }
}
