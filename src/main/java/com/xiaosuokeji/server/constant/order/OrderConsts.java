package com.xiaosuokeji.server.constant.order;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 订单Consts
 * Created by kunye on 2018/05/06.
 */
public class OrderConsts {

	public static XSStatusPair ORDER_EXIST = XSStatusPair.build(24000, "订单已存在");

	public static XSStatusPair ORDER_NOT_EXIST = XSStatusPair.build(24001, "订单不存在");

	public static XSStatusPair ORDER_IMPREST_NOT_MORE_AMOUNT = XSStatusPair.build(24002, "预付款不能多于订单金额");
}
