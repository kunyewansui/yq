package com.xiaosuokeji.server.constant.system;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 版本Consts
 * Created by gustinlau on 2017/10/28.
 */
public class VersionConsts {

    public static XSStatusPair VERSION_EXIST = XSStatusPair.build(10600, "版本已存在");

    public static XSStatusPair VERSION_NOT_EXIST = XSStatusPair.build(10601, "版本不存在");

    public static XSStatusPair VERSION_IS_LATEST = XSStatusPair.build(10602, "已经是最新版本");
}
