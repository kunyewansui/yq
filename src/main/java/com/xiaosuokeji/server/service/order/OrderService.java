package com.xiaosuokeji.server.service.order;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.order.OrderConsts;
import com.xiaosuokeji.server.dao.order.OrderDao;
import com.xiaosuokeji.server.dao.order.OrderItemDao;
import com.xiaosuokeji.server.model.order.Order;
import com.xiaosuokeji.server.model.order.OrderItem;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.util.CollectionUtils;
import com.xiaosuokeji.server.util.OrderGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单Service
 * Created by kunye on 2018/05/06.
 */
@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderItemDao orderItemDao;

	@Transactional
	public void save(Order order) throws XSBusinessException {
		OrderGenerator generator = OrderGenerator.INSTANCE;
		order.setOrderNo(generator.generate());
		order.setStatus(0);
		SecStaff admin = (SecStaff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		order.setCreator(admin.getName());
		orderDao.save(order);

		if(!CollectionUtils.isBlank(order.getOrderItemList())){
			order.getOrderItemList().stream().forEach(a -> a.setOrderNo(order.getOrderNo()));
			orderItemDao.batchInsert(order.getOrderItemList());
		}
		//TODO 客户欠款增加
	}

	@Transactional
	public void remove(Order order) throws XSBusinessException {
		Order existent = get(order);
		orderDao.remove(existent);
		orderItemDao.batchDelete(existent.getOrderNo());
	}

	@Transactional
	public void update(Order order) throws XSBusinessException {
		Order existent = orderDao.get(order);
		if(order.getOrderItemList()!=null){
			orderItemDao.batchDelete(existent.getOrderNo());
			if(order.getOrderItemList().size()>0){
				order.getOrderItemList().stream().forEach(a -> a.setOrderNo(existent.getOrderNo()));
				orderItemDao.batchInsert(order.getOrderItemList());
			}
		}
		orderDao.update(order);
	}

	public Order get(Order order) throws XSBusinessException {
		Order existent = orderDao.get(order);
		if (existent == null) {
			throw new XSBusinessException(OrderConsts.ORDER_NOT_EXIST);
		}
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderNo(existent.getOrderNo());
		existent.setOrderItemList(orderItemDao.list(orderItem));
		return existent;
	}

	public XSPageModel<Order> listAndCount(Order order) {
		order.setDefaultSort("a.id", "DESC");
		return XSPageModel.build(orderDao.list(order), orderDao.count(order));
	}
}
