package com.xiaosuokeji.server.service.impl.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.system.DictConsts;
import com.xiaosuokeji.server.dao.system.DictDao;
import com.xiaosuokeji.server.model.system.Dict;
import com.xiaosuokeji.server.service.intf.system.DictService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 字典Service
 * Created by xuxiaowei on 2017/11/1.
 */
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public void save(Dict dict) throws XSBusinessException {
        Dict existent = new Dict();
        existent.setKey(dict.getKey());
        Long count = dictDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(DictConsts.DICT_EXIST);
        }
        dictDao.save(dict);
    }

    @Override
    public void remove(Dict dict) throws XSBusinessException {
        Dict existent = get(dict);
        if (existent.getLock().equals(1)) {
            throw new XSBusinessException(DictConsts.DICT_LOCKED);
        }
        dictDao.remove(existent);
    }

    @Override
    public void update(Dict dict) throws XSBusinessException {
        Dict existent = get(dict);
        if (dict.getKey() != null) {
            Dict existDict = new Dict();
            existDict.setKey(dict.getKey());
            List<Dict> existents = dictDao.list(existDict);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(existent.getId());
                if (!isSelf) {
                    throw new XSBusinessException(DictConsts.DICT_EXIST);
                }
            }
        }
        dictDao.update(dict);
    }

    @Override
    public Dict updateLock(Dict dict) throws XSBusinessException {
        Dict existent = get(dict);
        Dict latest = new Dict(existent.getId());
        if (existent.getLock().equals(0)) {
            latest.setLock(1);
        }
        else {
            latest.setLock(0);
        }
        dictDao.updateLock(dict);
        return latest;
    }

    @Override
    public Dict get(Dict dict) throws XSBusinessException {
        Dict existent = dictDao.get(dict);
        if (existent == null) {
            throw new XSBusinessException(DictConsts.DICT_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public XSPageModel<Dict> listAndCount(Dict dict) {
        dict.setDefaultSort("id", "DESC");
        return XSPageModel.build(dictDao.list(dict), dictDao.count(dict));
    }
}
