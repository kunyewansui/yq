package com.xiaosuokeji.server.constant.product;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 产品类型Consts
 * Created by kunye on 2018/05/06.
 */
public class CategoryConsts {

	public static XSStatusPair CATEGORY_EXIST = XSStatusPair.build(24000, "产品类型已存在");

	public static XSStatusPair CATEGORY_NOT_EXIST = XSStatusPair.build(24001, "产品类型不存在");
}
