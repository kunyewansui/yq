package com.xiaosuokeji.server.dao.statistics;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 统计dao
 * User: guobaikun
 * Date: 2018/2/6
 * Time: 20:04
 */
public interface StatisticsDao {

    List<Map<String,Object>> orderFlow(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Map<String, Object> monthlyOrder(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Map<String, Object> monthPayment();

    Map<String, Object> monthlyRepay(@Param("startTime") String startTime, @Param("endTime") String endTime);
}

