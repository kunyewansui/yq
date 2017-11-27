package com.xiaosuokeji.server.constant.security;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 系统组织Consts
 * Created by xuxiaowei on 2017/10/27.
 */
public class SecOrganizationConsts {

    public static final XSStatusPair SEC_ORGANIZATION_EXIST = XSStatusPair.build(10006, "组织已存在");

    public static final XSStatusPair SEC_ORGANIZATION_USED = XSStatusPair.build(10007, "组织被使用");

    public static final XSStatusPair SEC_ORGANIZATION_NOT_EXIST = XSStatusPair.build(10008, "组织不存在");
}
