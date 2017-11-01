package com.xiaosuokeji.server.constant.system;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 版本Consts
 * Created by gustinlau on 2017/10/28.
 */
public class VersionConsts {

    public static XSStatusPair VERSION_EXIST = XSStatusPair.build(20000, "版本已存在");

    public static XSStatusPair VERSION_NOT_EXIST = XSStatusPair.build(20003, "版本不存在");
}
