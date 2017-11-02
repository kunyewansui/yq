package com.xiaosuokeji.server.dao.security;

import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.model.security.SecRole;

import java.util.List;

/**
 * 系统资源Dao
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecResourceDao {

    int save(SecResource secResource);

    /**
     * 赋予超级管理员指定资源
     * @param secResource 参数id
     * @return 受影响行数
     */
    int saveSuperiorRes(SecResource secResource);

    int remove(SecResource secResource);

    /**
     * 删除超级管理员指定资源
     * @param secResource 参数id
     * @return 受影响行数
     */
    int removeSuperiorRes(SecResource secResource);

    int update(SecResource secResource);

    int batchUpdate(SecResource secResource);

    SecResource get(SecResource secResource);

    List<SecResource> list(SecResource secResource);

    List<SecResource> listCombo(SecResource secResource);

    /**
     * 获取指定请求的资源列表
     * @param secResource 参数url，method
     * @return 资源列表
     */
    List<SecResource> listByRequest(SecResource secResource);

    /**
     * 获取拥有指定多个资源的角色列表
     * @param secResource 参数list
     * @return 角色列表
     */
    List<SecRole> listRole(SecResource secResource);

    Long count(SecResource secResource);

    /**
     * 获取拥有指定资源的角色数量
     * @param secResource 参数id
     * @return 角色数量
     */
    Long countRole(SecResource secResource);
}
