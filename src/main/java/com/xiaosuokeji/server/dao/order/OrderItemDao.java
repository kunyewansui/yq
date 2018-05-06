package com.xiaosuokeji.server.dao.order;

import com.xiaosuokeji.server.model.order.OrderItem;
import java.util.List;

/**
 * 订单详情Dao
 * Created by kunye on 2018/05/06.
 */
public interface OrderItemDao {

	int save(OrderItem orderItem);

	int remove(OrderItem orderItem);

	int update(OrderItem orderItem);

	OrderItem get(OrderItem orderItem);

	List<OrderItem> list(OrderItem orderItem);

	Long count(OrderItem orderItem);
}
