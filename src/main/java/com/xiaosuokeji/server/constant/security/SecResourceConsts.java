package com.xiaosuokeji.server.constant.security;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 系统资源Consts
 * Created by xuxiaowei on 2017/10/27.
 */
public class SecResourceConsts {

    public static final XSStatusPair SEC_RESOURCE_EXIST = XSStatusPair.build(10100, "资源已存在");

    public static final XSStatusPair SEC_RESOURCE_NOT_EXIST = XSStatusPair.build(10101, "资源不存在");

    public static final XSStatusPair SEC_RESOURCE_USED = XSStatusPair.build(10102, "资源被使用");
}
