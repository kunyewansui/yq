package com.xiaosuokeji.server.dao.system;

import com.xiaosuokeji.server.model.system.Country;
import java.util.List;

/**
 * 国家Dao
 * Created by xuxiaowei on 2018/5/3.
 */
public interface CountryDao {

    int save(Country country);

    int remove(Country country);

    int update(Country country);

    int updateStatus(Country country);

    Country get(Country country);

    List<Country> list(Country country);

    List<Country> listCombo(Country country);

    Long count(Country country);

    int batchUpdate(List<Country> list);
}
