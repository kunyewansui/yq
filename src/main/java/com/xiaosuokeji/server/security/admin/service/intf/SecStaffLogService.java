package com.xiaosuokeji.server.security.admin.service.intf;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.intf.XSLogHandler;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.security.admin.model.SecStaffLog;

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
