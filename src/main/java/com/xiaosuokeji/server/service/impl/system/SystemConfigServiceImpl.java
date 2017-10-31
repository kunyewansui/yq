package com.xiaosuokeji.server.service.impl.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSUuidUtil;
import com.xiaosuokeji.server.constant.system.SystemConfigConsts;
import com.xiaosuokeji.server.dao.system.SystemConfigDao;
import com.xiaosuokeji.server.model.system.SystemConfig;
import com.xiaosuokeji.server.service.intf.system.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统配置ServiceImpl
 * Created by xuxiaowei on 2017/10/28.
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    
    @Autowired
    private SystemConfigDao systemConfigDao;

    @Override
    public void save(SystemConfig systemConfig) throws XSBusinessException {
        SystemConfig existent = new SystemConfig();
        existent.setKey(systemConfig.getKey());
        Long count = systemConfigDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SystemConfigConsts.SYSTEM_CONFIG_EXIST);
        }
        systemConfig.setId(XSUuidUtil.generate());
        systemConfigDao.save(systemConfig);
    }

    @Override
    public void remove(SystemConfig systemConfig) throws XSBusinessException {
        SystemConfig existent = get(systemConfig);
        systemConfigDao.remove(existent);
    }

    @Override
    public void update(SystemConfig systemConfig) throws XSBusinessException {
        get(systemConfig);
        if (systemConfig.getKey() != null) {
            SystemConfig existent = new SystemConfig();
            existent.setKey(systemConfig.getKey());
            List<SystemConfig> existents = systemConfigDao.list(existent);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(systemConfig.getId());
                if (!isSelf) {
                    throw new XSBusinessException(SystemConfigConsts.SYSTEM_CONFIG_EXIST);
                }
            }
        }
        systemConfigDao.update(systemConfig);
    }

    @Override
    public SystemConfig get(SystemConfig systemConfig) throws XSBusinessException {
        SystemConfig existent = systemConfigDao.get(systemConfig);
        if (existent == null) {
            throw new XSBusinessException(SystemConfigConsts.SYSTEM_CONFIG_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public XSPageModel<SystemConfig> listAndCount(SystemConfig systemConfig) {
        systemConfig.setDefaultSort("create_time", "DESC");
        return XSPageModel.build(systemConfigDao.list(systemConfig), systemConfigDao.count(systemConfig));
    }
}
