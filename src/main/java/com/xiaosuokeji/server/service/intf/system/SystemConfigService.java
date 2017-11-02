package com.xiaosuokeji.server.service.intf.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.system.SystemConfig;

/**
 * 系统配置Service
 * Created by xuxiaowei on 2017/10/28.
 */
public interface SystemConfigService {

    void save(SystemConfig systemConfig) throws XSBusinessException;

    void remove(SystemConfig systemConfig) throws XSBusinessException;

    void update(SystemConfig systemConfig) throws XSBusinessException;

    SystemConfig get(SystemConfig systemConfig) throws XSBusinessException;

    XSPageModel<SystemConfig> listAndCount(SystemConfig systemConfig);
}
