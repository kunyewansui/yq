package com.xiaosuokeji.server.dao.security;

import com.xiaosuokeji.server.model.security.SecResource;

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

    /**
     * 批量删除指定资源和角色的映射
     * @param secResource
     * @return 受影响行数
     */
    int batchRemoveRoleRes(SecResource secResource);

    int update(SecResource secResource);

    int batchUpdate(SecResource secResource);

    SecResource get(SecResource secResource);

    List<SecResource> list(SecResource secResource);

    List<SecResource> listCombo(SecResource secResource);

    /**
     * 获取所有资源和其所属的角色列表
     * @return 资源列表
     */
    List<SecResource> listAll();

    Long count(SecResource secResource);

    /**
     * 获取拥有指定资源的角色数量
     * @param secResource 参数id
     * @return 角色数量
     */
    Long countRole(SecResource secResource);
}
