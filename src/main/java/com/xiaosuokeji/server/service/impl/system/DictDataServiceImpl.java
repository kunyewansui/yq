package com.xiaosuokeji.server.service.impl.system;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.system.DictDataConsts;
import com.xiaosuokeji.server.dao.system.DictDataDao;
import com.xiaosuokeji.server.model.system.Dict;
import com.xiaosuokeji.server.model.system.DictData;
import com.xiaosuokeji.server.service.intf.system.DictDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 数据字典ServiceImpl
 * Created by xuxiaowei on 2017/11/1.
 */
@Service
public class DictDataServiceImpl implements DictDataService {

    private static final Logger logger = LoggerFactory.getLogger(DictDataServiceImpl.class);

    @Autowired
    private DictDataDao dictDataDao;

    private Cache<String, Map<String, String>> cache = null;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder().expireAfterAccess(1800L, TimeUnit.SECONDS).maximumSize(512).build();
    }

    @Override
    public void save(DictData dictData) throws XSBusinessException {
        DictData criteria = new DictData();
        criteria.setDict(dictData.getDict());
        criteria.setValue(dictData.getValue());
        Long count = dictDataDao.count(criteria);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(DictDataConsts.DICT_DATA_EXIST);
        }
        dictDataDao.save(dictData);
        //清除该数据所属字典的缓存
        DictData existent = dictDataDao.get(dictData);
        cache.invalidate(existent.getDict().getKey());
    }

    @Override
    public void remove(DictData dictData) throws XSBusinessException {
        DictData existent = get(dictData);
        if (existent.getLock().equals(1)) {
            throw new XSBusinessException(DictDataConsts.DICT_DATA_LOCKED);
        }
        dictDataDao.remove(existent);
        //清除该数据所属字典的缓存
        cache.invalidate(existent.getDict().getKey());
    }

    @Override
    public void update(DictData dictData) throws XSBusinessException {
        DictData existent = get(dictData);
        if (dictData.getDict() != null || dictData.getValue() != null) {
            DictData criteria = new DictData();
            criteria.setDict(dictData.getDict() == null ? existent.getDict() : dictData.getDict());
            criteria.setValue(dictData.getValue() == null ? existent.getValue() : dictData.getValue());
            List<DictData> existents = dictDataDao.list(criteria);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(existent.getId());
                if (!isSelf) {
                    throw new XSBusinessException(DictDataConsts.DICT_DATA_EXIST);
                }
            }
        }
        dictDataDao.update(dictData);
        //清除该数据所属字典的缓存
        cache.invalidate(existent.getDict().getKey());
    }

    @Override
    public void updateLock(DictData dictData) throws XSBusinessException {
        DictData existent = get(dictData);
        DictData latest = new DictData(existent.getId());
        if (existent.getLock().equals(0)) {
            latest.setLock(1);
        }
        else {
            latest.setLock(0);
        }
        dictDataDao.updateLock(latest);
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
        return mapByDict(dictKey).get(dictDataValue);
    }

    @Override
    public List<DictData> list(DictData dictData) {
        dictData.setDefaultSort("id", "DESC");
        return dictDataDao.list(dictData);
    }

    @Override
    public XSPageModel<DictData> listAndCount(DictData dictData) {
        dictData.setDefaultSort("id", "DESC");
        return XSPageModel.build(dictDataDao.list(dictData), dictDataDao.count(dictData));
    }

    @Override
    public Map<String, String> mapByDict(String dictKey) {
        Map<String, String> map = null;
        //先查询缓存，若未命中则查询数据库
        try {
            map = cache.get(dictKey, new Callable<Map<String, String>>() {
                @Override
                public Map<String, String> call() {
                    logger.debug("Guava缓存未命中，从数据库中获取字典数据，key=" + dictKey);
                    List<DictData> list = dictDataDao.listByDict(new Dict(dictKey));
                    Map<String, String> map = new HashMap<>();
                    for (DictData item : list) {
                        map.put(item.getValue(), item.getDesc());
                    }
                    return map;
                }
            });
        } catch (Exception e) {
            logger.error("error : ", e);
        }
        return map == null ? new HashMap<>() : map;
    }
}
