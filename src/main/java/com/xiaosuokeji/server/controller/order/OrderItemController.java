package com.xiaosuokeji.server.controller.order;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.order.OrderItem;
import com.xiaosuokeji.server.service.order.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单详情Controller
 * Created by kunye on 2018/05/06.
 */
@Controller
@XSLog
@XSExceptionHandler
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;

	@RequestMapping(value = "/admin/order/orderItem", method = RequestMethod.GET)
	public String index(Model model, OrderItem orderItem) {
		if(orderItem.getPage() == null) {
			orderItem.setPage(1L);
		}
		model.addAttribute("search", orderItem);
		model.addAttribute("pageModel", orderItemService.listAndCount(orderItem));
		return "admin/order/orderItem";
	}

	@RequestMapping(value = "/admin/order/orderItem/get", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult get(OrderItem orderItem) throws XSBusinessException {
		return XSServiceResult.build().data(orderItemService.get(orderItem));
	}

	@RequestMapping(value = "/admin/order/orderItem/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(OrderItem.Save.class) OrderItem orderItem) throws XSBusinessException {
		orderItemService.save(orderItem);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/order/orderItem/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(OrderItem orderItem) throws XSBusinessException {
		orderItemService.remove(orderItem);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/order/orderItem/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(OrderItem.Update.class) OrderItem orderItem) throws XSBusinessException {
		orderItemService.update(orderItem);
		return XSServiceResult.build();
	}
}
