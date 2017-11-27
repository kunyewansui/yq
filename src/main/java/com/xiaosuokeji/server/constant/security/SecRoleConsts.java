package com.xiaosuokeji.server.constant.security;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 系统角色Consts
 * Created by xuxiaowei on 2017/10/27.
 */
public class SecRoleConsts {

    public static final XSStatusPair SEC_ROLE_EXIST = XSStatusPair.build(10003, "角色已存在");

    public static final XSStatusPair SEC_ROLE_USED = XSStatusPair.build(10004, "角色被使用");

    public static final XSStatusPair SEC_ROLE_NOT_EXIST = XSStatusPair.build(10005, "角色不存在");
}
