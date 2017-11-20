package com.xiaosuokeji.server.constant.security;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 系统用户Consts
 * Created by xuxiaowei on 2017/10/27.
 */
public class SecStaffConsts {

    public static final XSStatusPair SEC_STAFF_EXIST = XSStatusPair.build(10130, "员工已存在");

    public static final XSStatusPair SEC_STAFF_NOT_EXIST = XSStatusPair.build(10131, "员工不存在");

    public static final XSStatusPair SEC_STAFF_NOT_ENABLE = XSStatusPair.build(10132, "员工被禁用");

    public static final XSStatusPair SEC_STAFF_ACCESS_DENY = XSStatusPair.build(10133, "您的访问被拒绝");

    public static final XSStatusPair SEC_STAFF_PASSWORD_ERROR = XSStatusPair.build(10134, "密码错误");

    public static final XSStatusPair SEC_STAFF_CONCURRENT_BEYOND = XSStatusPair.build(10135, "该账号超出最大登录人数限制");

    public static final XSStatusPair SEC_STAFF_SESSION_TIMEOUT = XSStatusPair.build(10136, "会话超时，请重新登录");
}
