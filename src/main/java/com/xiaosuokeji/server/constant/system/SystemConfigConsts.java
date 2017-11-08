package com.xiaosuokeji.server.constant.system;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 系统配置Consts
 * Created by xuxiaowei on 2017/10/28.
 */
public class SystemConfigConsts {

    public static XSStatusPair SYSTEM_CONFIG_EXIST = XSStatusPair.build(10200, "系统配置已存在");

    public static XSStatusPair SYSTEM_CONFIG_NOT_EXIST = XSStatusPair.build(10201, "系统配置不存在");
}
