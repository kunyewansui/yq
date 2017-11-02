package com.xiaosuokeji.server.security.admin.dao;

import com.xiaosuokeji.server.security.admin.model.SecResource;
import com.xiaosuokeji.server.security.admin.model.SecStaffLog;

import java.util.List;

/**
 * 系统用户日志Dao
 * Created by xuxiaowei on 2017/11/2.
 */
public interface SecStaffLogDao {

    int save(SecStaffLog secStaffLog);

    int remove(SecStaffLog secStaffLog);

    SecStaffLog get(SecStaffLog secStaffLog);

    List<SecStaffLog> list(SecStaffLog secStaffLog);

    /**
     * 获取指定请求的资源列表
     * @param secResource 参数url，method
     * @return 资源列表
     */
    List<SecResource> listResourceByRequest(SecResource secResource);

    Long count(SecStaffLog secStaffLog);
}
