package com.xiaosuokeji.server.dao.system;


import com.xiaosuokeji.server.model.system.Version;

import java.util.List;

/**
 * Created by gustinlau on 11/1/17.
 */
public interface VersionDao {
    
    int save(Version version);

    int remove(Version version);

    int update(Version version);

    Version get(Version version);

    List<Version> list(Version version);

    Long count(Version version);
}
