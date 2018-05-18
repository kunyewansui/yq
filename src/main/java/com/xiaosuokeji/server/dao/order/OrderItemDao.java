package com.xiaosuokeji.server.dao.order;

import com.xiaosuokeji.server.model.order.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单详情Dao
 * Created by kunye on 2018/05/06.
 */
public interface OrderItemDao {

    int batchInsert(List<OrderItem> orderItemList);

	int batchDelete(@Param("orderNo") String orderNo);

	List<OrderItem> list(OrderItem orderItem);
}
