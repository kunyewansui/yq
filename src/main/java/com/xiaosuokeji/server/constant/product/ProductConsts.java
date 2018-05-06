package com.xiaosuokeji.server.constant.product;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 产品Consts
 * Created by kunye on 2018/05/06.
 */
public class ProductConsts {

	public static XSStatusPair PRODUCT_EXIST = XSStatusPair.build(24000, "产品已存在");

	public static XSStatusPair PRODUCT_NOT_EXIST = XSStatusPair.build(24001, "产品不存在");
}
