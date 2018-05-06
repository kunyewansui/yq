package com.xiaosuokeji.server.service.order;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.order.OrderConsts;
import com.xiaosuokeji.server.dao.order.OrderDao;
import com.xiaosuokeji.server.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;

	public void save(Order order) throws XSBusinessException {
		orderDao.save(order);
	}

	public void remove(Order order) throws XSBusinessException {
		Order existent = get(order);
		orderDao.remove(existent);
	}

	public void update(Order order) throws XSBusinessException {
		Order existent = get(order);
		orderDao.update(order);
	}

	public Order get(Order order) throws XSBusinessException {
		Order existent = orderDao.get(order);
		if (existent == null) {
			throw new XSBusinessException(OrderConsts.ORDER_NOT_EXIST);
		}
		return existent;
	}

	public XSPageModel<Order> listAndCount(Order order) {
		order.setDefaultSort("id", "DESC");
		return XSPageModel.build(orderDao.list(order), orderDao.count(order));
	}
}
