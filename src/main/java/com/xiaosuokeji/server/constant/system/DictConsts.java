package com.xiaosuokeji.server.constant.system;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 字典Consts
 * Created by xuxiaowei on 2017/11/1.
 */
public class DictConsts {

    public static XSStatusPair DICT_EXIST = XSStatusPair.build(10100, "字典已存在");

    public static XSStatusPair DICT_NOT_EXIST = XSStatusPair.build(10101, "字典不存在");

    public static XSStatusPair DICT_LOCKED = XSStatusPair.build(10102, "字典被锁定");

    public static XSStatusPair DICT_USED = XSStatusPair.build(10103, "字典被使用");
}
