package com.xiaosuokeji.server.service.order;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.order.OrderItemConsts;
import com.xiaosuokeji.server.dao.order.OrderItemDao;
import com.xiaosuokeji.server.model.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单详情ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao orderItemDao;

	public void save(OrderItem orderItem) throws XSBusinessException {
		orderItemDao.save(orderItem);
	}

	public void remove(OrderItem orderItem) throws XSBusinessException {
		OrderItem existent = get(orderItem);
		orderItemDao.remove(existent);
	}

	public void update(OrderItem orderItem) throws XSBusinessException {
		OrderItem existent = get(orderItem);
		orderItemDao.update(orderItem);
	}

	public OrderItem get(OrderItem orderItem) throws XSBusinessException {
		OrderItem existent = orderItemDao.get(orderItem);
		if (existent == null) {
			throw new XSBusinessException(OrderItemConsts.ORDER_ITEM_NOT_EXIST);
		}
		return existent;
	}

	public XSPageModel<OrderItem> listAndCount(OrderItem orderItem) {
		orderItem.setDefaultSort("id", "DESC");
		return XSPageModel.build(orderItemDao.list(orderItem), orderItemDao.count(orderItem));
	}
}
