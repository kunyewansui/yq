package com.xiaosuokeji.server.service.intf.security;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.intf.XSLogHandler;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.security.SecStaffLog;

/**
 * 系统用户日志Service
 * Created by xuxiaowei on 2017/11/2.
 */
public interface SecStaffLogService extends XSLogHandler {

    void save(SecStaffLog secStaffLog);

    void remove(SecStaffLog secStaffLog) throws XSBusinessException;

    SecStaffLog get(SecStaffLog secStaffLog) throws XSBusinessException;

    XSPageModel<SecStaffLog> listAndCount(SecStaffLog secStaffLog);
}
