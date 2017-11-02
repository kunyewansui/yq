package com.xiaosuokeji.server.service.impl.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.system.DictDataConsts;
import com.xiaosuokeji.server.dao.system.DictDataDao;
import com.xiaosuokeji.server.model.system.Dict;
import com.xiaosuokeji.server.model.system.DictData;
import com.xiaosuokeji.server.service.intf.system.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典ServiceImpl
 * Created by xuxiaowei on 2017/11/1.
 */
@Service
public class DictDataServiceImpl implements DictDataService {

    @Autowired
    private DictDataDao dictDataDao;

    @Override
    public void save(DictData dictData) throws XSBusinessException {
        DictData existent = new DictData();
        existent.setDict(dictData.getDict());
        existent.setValue(dictData.getValue());
        Long count = dictDataDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(DictDataConsts.DICT_DATA_EXIST);
        }
        dictDataDao.save(dictData);
    }

    @Override
    public void remove(DictData dictData) throws XSBusinessException {
        DictData existent = get(dictData);
        if (existent.getLock().equals(1)) {
            throw new XSBusinessException(DictDataConsts.DICT_DATA_LOCKED);
        }
        dictDataDao.remove(existent);
    }

    @Override
    public void update(DictData dictData) throws XSBusinessException {
        DictData existent = get(dictData);
        if (dictData.getDict() != null || dictData.getValue() != null) {
            DictData existDict = new DictData();
            existDict.setDict(dictData.getDict() == null ? existent.getDict() : dictData.getDict());
            existDict.setValue(dictData.getValue() == null ? existDict.getValue() : dictData.getValue());
            List<DictData> existents = dictDataDao.list(existDict);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(existent.getId());
                if (!isSelf) {
                    throw new XSBusinessException(DictDataConsts.DICT_DATA_EXIST);
                }
            }
        }
        dictDataDao.update(dictData);
    }

    @Override
    public DictData updateLock(DictData dictData) throws XSBusinessException {
        DictData existent = get(dictData);
        DictData latest = new DictData(existent.getId());
        if (existent.getLock().equals(0)) {
            latest.setLock(1);
        }
        else {
            latest.setLock(0);
        }
        dictDataDao.updateLock(dictData);
        return latest;
    }

    @Override
    public DictData get(DictData dictData) throws XSBusinessException {
        DictData existent = dictDataDao.get(dictData);
        if (existent == null) {
            throw new XSBusinessException(DictDataConsts.DICT_DATA_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public String getDesc(String dictKey, String dictDataValue) {
        Dict dict = new Dict();
        dict.setKey(dictKey);
        DictData dictData = new DictData();
        dictData.setDict(dict);
        dictData.setValue(dictDataValue);
        DictData existent = dictDataDao.getDesc(dictData);
        if (existent != null) {
            return existent.getDesc();
        }
        return null;
    }

    @Override
    public XSPageModel<DictData> listAndCount(DictData dictData) {
        dictData.setDefaultSort("id", "DESC");
        return XSPageModel.build(dictDataDao.list(dictData), dictDataDao.count(dictData));
    }

    @Override
    public List<DictData> listByDict(String dictKey) {
        Dict dict = new Dict();
        dict.setKey(dictKey);
        return dictDataDao.listByDict(dict);
    }
}
