package com.xiaosuokeji.server.security.admin.dao;

import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecRole;

import java.util.List;

/**
 * 系统资源Dao
 * Created by xuxiaowei on 2017/10/27.
 */
public interface SecResourceDao {

    int save(SecResource secResource);

    int remove(SecResource secResource);

    int update(SecResource secResource);

    SecResource get(SecResource secResource);

    List<SecResource> list(SecResource secResource);

    List<SecResource> listCombo(SecResource secResource);

    /**
     * 根据请求内容获取资源列表
     * @param secResource 查询条件url，method
     * @return 资源列表
     */
    List<SecResource> listByRequest(SecResource secResource);

    /**
     * 获取拥有资源列表中资源的角色
     * @param secResource 查询条件list
     * @return 角色列表
     */
    List<SecRole> listRole(SecResource secResource);

    Long count(SecResource secResource);

    /**
     * 获取拥有该资源的角色数量
     * @param secResource 查询条件id
     * @return 角色数量
     */
    Long countRole(SecResource secResource);
}
