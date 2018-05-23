package com.xiaosuokeji.server.constant.system;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 国家Consts
 * Created by xuxiaowei on 2018/5/3.
 */
public class CountryConsts {

    public static XSStatusPair COUNTRY_EXIST = XSStatusPair.build(10506, "国家已存在");

    public static XSStatusPair COUNTRY_USED = XSStatusPair.build(10507, "国家被使用");

    public static XSStatusPair COUNTRY_NOT_EXIST = XSStatusPair.build(10508, "国家不存在");
}
