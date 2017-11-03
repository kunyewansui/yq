package com.xiaosuokeji.server.constant.security;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 系统用户Consts
 * Created by xuxiaowei on 2017/10/27.
 */
public class SecStaffConsts {

    public static final XSStatusPair SEC_STAFF_EXIST = XSStatusPair.build(10300, "员工已存在");

    public static final XSStatusPair SEC_STAFF_NOT_EXIST = XSStatusPair.build(10302, "员工不存在");

    public static final XSStatusPair SEC_STAFF_NOT_ENABLE = XSStatusPair.build(10303, "员工被禁用");

    public static final XSStatusPair SEC_STAFF_ACCESS_DENY = XSStatusPair.build(10304, "您的访问被拒绝");

    public static final XSStatusPair SEC_STAFF_PASSWORD_ERROR = XSStatusPair.build(701, "密码错误");

    public static final XSStatusPair SEC_STAFF_CONCURRENT_BEYOND = XSStatusPair.build(703, "该账号超出最大登录人数限制");
}