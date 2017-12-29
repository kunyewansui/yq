package com.xiaosuokeji.server.dao.system;

import com.xiaosuokeji.server.model.system.SystemConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统配置Dao
 * Created by xuxiaowei on 2017/10/28.
 */
public interface SystemConfigDao {

    int save(SystemConfig systemConfig);

    int remove(SystemConfig systemConfig);

    int update(SystemConfig systemConfig);

    SystemConfig get(SystemConfig systemConfig);

    SystemConfig getByKey(@Param("key") String key);

    List<SystemConfig> list(SystemConfig systemConfig);

    Long count(SystemConfig systemConfig);
}
