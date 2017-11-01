package com.xiaosuokeji.server.dao.system;

import com.xiaosuokeji.server.model.system.Dict;

import java.util.List;

/**
 * 字典Dao
 * Created by xuxiaowei on 2017/11/1.
 */
public interface DictDao {

    int save(Dict dict);

    int remove(Dict dict);

    int update(Dict dict);

    int updateLock(Dict dict);

    Dict get(Dict dict);

    List<Dict> list(Dict dict);

    Long count(Dict dict);
}
