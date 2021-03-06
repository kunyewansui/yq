package com.xiaosuokeji.server.constant.system;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 字典数据Consts
 * Created by xuxiaowei on 2017/11/1.
 */
public class DictDataConsts {

    public static XSStatusPair DICT_DATA_EXIST = XSStatusPair.build(10104, "字典数据已存在");

    public static XSStatusPair DICT_DATA_NOT_EXIST = XSStatusPair.build(10105, "字典数据不存在");

    public static XSStatusPair DICT_DATA_LOCKED = XSStatusPair.build(10106, "字典数据被锁定");
}
