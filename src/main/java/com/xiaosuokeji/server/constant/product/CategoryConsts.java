package com.xiaosuokeji.server.constant.product;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 产品类型Consts
 * Created by kunye on 2018/05/06.
 */
public class CategoryConsts {

	public static XSStatusPair CATEGORY_EXIST = XSStatusPair.build(24000, "产品类型已存在");

	public static XSStatusPair CATEGORY_NOT_EXIST = XSStatusPair.build(24001, "产品类型不存在");

	public static XSStatusPair CATEGORY_USED = XSStatusPair.build(24002, "产品类型被使用");

	public static XSStatusPair CATEGORY_NOT_SELF_OR_SUB = XSStatusPair.build(24003, "不能选择自己或自己的下级作为父级");
}
