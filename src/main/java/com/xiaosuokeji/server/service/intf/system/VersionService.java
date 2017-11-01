package com.xiaosuokeji.server.service.intf.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.system.Version;

/**
 * Created by gustinlau on 11/1/17.
 */
public interface VersionService {

    void save(Version version) throws XSBusinessException;

    void remove(Version version) throws XSBusinessException;

    void update(Version version) throws XSBusinessException;

    Version get(Version version) throws XSBusinessException;

    XSPageModel<Version> listAndCount(Version version);
}
