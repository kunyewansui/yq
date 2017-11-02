package com.xiaosuokeji.server.dao.system;

import com.xiaosuokeji.server.model.system.Dict;
import com.xiaosuokeji.server.model.system.DictData;

import java.util.List;

/**
 * 字典数据Dao
 * Created by xuxiaowei on 2017/11/1.
 */
public interface DictDataDao {

    int save(DictData dictData);

    int remove(DictData dictData);

    int update(DictData dictData);

    int updateLock(DictData dictData);

    DictData get(DictData dictData);

    DictData getDesc(DictData dictData);

    List<DictData> list(DictData dictData);

    List<DictData> listByDict(Dict dict);

    Long count(DictData dictData);
}
