package com.xiaosuokeji.server.constant.order;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 还款Consts
 * Created by kunye on 2018/05/06.
 */
public class PaymentConsts {

	public static XSStatusPair PAYMENT_EXIST = XSStatusPair.build(24000, "还款已存在");

	public static XSStatusPair PAYMENT_NOT_EXIST = XSStatusPair.build(24001, "还款不存在");
}
