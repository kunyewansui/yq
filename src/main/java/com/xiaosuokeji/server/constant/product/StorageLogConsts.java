package com.xiaosuokeji.server.constant.product;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 库存日志Consts
 * Created by kunye on 2018/06/02.
 */
public class StorageLogConsts {

	public static XSStatusPair STORAGE_LOG_EXIST = XSStatusPair.build(14000, "库存日志已存在");

	public static XSStatusPair STORAGE_LOG_NOT_EXIST = XSStatusPair.build(14001, "库存日志不存在");
}
