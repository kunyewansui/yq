package com.xiaosuokeji.server.constant.order;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 订单详情Consts
 * Created by kunye on 2018/05/06.
 */
public class OrderItemConsts {

	public static XSStatusPair ORDER_ITEM_EXIST = XSStatusPair.build(24000, "订单详情已存在");

	public static XSStatusPair ORDER_ITEM_NOT_EXIST = XSStatusPair.build(24001, "订单详情不存在");
}
