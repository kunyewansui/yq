package com.xiaosuokeji.server.constant.security;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 系统组织Consts
 * Created by xuxiaowei on 2017/10/27.
 */
public class SecOrganizationConsts {

    public static final XSStatusPair SEC_ORGANIZATION_EXIST = XSStatusPair.build(10300, "组织已存在");

    public static final XSStatusPair SEC_ORGANIZATION_USED = XSStatusPair.build(10301, "组织被使用");

    public static final XSStatusPair SEC_ORGANIZATION_NOT_EXIST = XSStatusPair.build(10302, "组织不存在");
}
