package com.xiaosuokeji.server.service.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.system.VersionConsts;
import com.xiaosuokeji.server.dao.system.VersionDao;
import com.xiaosuokeji.server.model.system.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 版本管理ServiceImpl
 * Created by gustinlau on 11/1/17.
 */
@Service
public class VersionService {

    @Autowired
    VersionDao versionDao;

    public void save(Version version) throws XSBusinessException {
        Version existent = new Version();
        existent.setCode(version.getCode());
        existent.setPlatform(version.getPlatform());
        Long count = versionDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(VersionConsts.VERSION_EXIST);
        }
        versionDao.save(version);
    }

    public void remove(Version version) throws XSBusinessException {
        Version existent = get(version);
        versionDao.remove(existent);
    }

    public void update(Version version) throws XSBusinessException {
        versionDao.update(version);
    }

    public Version get(Version version) throws XSBusinessException {
        Version existent = versionDao.get(version);
        if (existent == null) {
            throw new XSBusinessException(VersionConsts.VERSION_NOT_EXIST);
        }
        return existent;
    }

    public XSPageModel<Version> listAndCount(Version version) {
        version.setDefaultSort("code", "DESC");
        return XSPageModel.build(versionDao.list(version), versionDao.count(version));
    }
}
