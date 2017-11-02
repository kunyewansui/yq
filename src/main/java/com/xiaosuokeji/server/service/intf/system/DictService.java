package com.xiaosuokeji.server.service.intf.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.system.Dict;

/**
 * 字典Service
 * Created by xuxiaowei on 2017/11/1.
 */
public interface DictService {

    void save(Dict dict) throws XSBusinessException;

    void remove(Dict dict) throws XSBusinessException;

    void update(Dict dict) throws XSBusinessException;

    Dict updateLock(Dict dict) throws XSBusinessException;

    Dict get(Dict dict) throws XSBusinessException;

    XSPageModel<Dict> listAndCount(Dict dict);
}
