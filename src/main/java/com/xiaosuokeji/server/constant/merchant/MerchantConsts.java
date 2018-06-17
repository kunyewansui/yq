package com.xiaosuokeji.server.constant.merchant;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 商户Consts
 * Created by kunye on 2018/05/06.
 */
public class MerchantConsts {

	public static XSStatusPair MERCHANT_EXIST = XSStatusPair.build(24000, "商户已存在");

	public static XSStatusPair MERCHANT_NOT_EXIST = XSStatusPair.build(24001, "商户不存在");

	public static XSStatusPair MERCHANT_DEBT_FLOW = XSStatusPair.build(24002, "还款金额不能超过欠款金额");
}
