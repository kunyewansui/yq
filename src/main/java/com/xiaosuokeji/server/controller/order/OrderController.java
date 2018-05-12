package com.xiaosuokeji.server.controller.order;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.order.Order;
import com.xiaosuokeji.server.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单Controller
 * Created by kunye on 2018/05/06.
 */
@Controller
@XSLog
@XSExceptionHandler
@RequestMapping("/admin/order/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/admin/order/order", method = RequestMethod.GET)
	public String index(Model model, Order order) {
		if(order.getPage() == null) {
			order.setPage(1L);
		}
		model.addAttribute("search", order);
		model.addAttribute("pageModel", orderService.listAndCount(order));
		return "admin/order/order";
	}

	@RequestMapping(value = "/admin/order/order/get", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult get(Order order) throws XSBusinessException {
		return XSServiceResult.build().data(orderService.get(order));
	}

	@RequestMapping(value = "/admin/order/order/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(Order.Save.class) Order order) throws XSBusinessException {
		orderService.save(order);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/order/order/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(Order order) throws XSBusinessException {
		orderService.remove(order);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/order/order/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(Order.Update.class) Order order) throws XSBusinessException {
		orderService.update(order);
		return XSServiceResult.build();
	}
}
