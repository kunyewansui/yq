package com.xiaosuokeji.server.dao.order;

import com.xiaosuokeji.server.model.order.Order;
import java.util.List;

/**
 * 订单Dao
 * Created by kunye on 2018/05/06.
 */
public interface OrderDao {

	int save(Order order);

	int remove(Order order);

	int update(Order order);

	Order get(Order order);

	List<Order> list(Order order);

	Long count(Order order);
}
