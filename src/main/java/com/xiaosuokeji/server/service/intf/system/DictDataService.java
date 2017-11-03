package com.xiaosuokeji.server.service.intf.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.intf.XSDictDataProvider;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.system.DictData;

import java.util.List;
import java.util.Map;

/**
 * 字典数据Service
 * Created by xuxiaowei on 2017/11/1.
 */
public interface DictDataService extends XSDictDataProvider {

    void save(DictData dictData) throws XSBusinessException;

    void remove(DictData dictData) throws XSBusinessException;

    void update(DictData dictData) throws XSBusinessException;

    void updateLock(DictData dictData) throws XSBusinessException;

    DictData get(DictData dictData) throws XSBusinessException;

    List<DictData> list(DictData dictData);

    XSPageModel<DictData> listAndCount(DictData dictData);

    /**
     * 根据指定字典键获取字典数据映射集
     * @param dictKey 字典键
     * @return 字典数据映射集
     */
    Map<String, String> mapByDict(String dictKey);
}
