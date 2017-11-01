package com.xiaosuokeji.server.service.intf.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.intf.XSDictDataProvider;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.system.DictData;

/**
 * 字典数据Service
 * Created by xuxiaowei on 2017/11/1.
 */
public interface DictDataService extends XSDictDataProvider {

    void save(DictData dictData) throws XSBusinessException;

    void remove(DictData dictData) throws XSBusinessException;

    void update(DictData dictData) throws XSBusinessException;

    DictData updateLock(DictData dictData) throws XSBusinessException;

    DictData get(DictData dictData) throws XSBusinessException;

    XSPageModel<DictData> listAndCount(DictData dictData);
}
