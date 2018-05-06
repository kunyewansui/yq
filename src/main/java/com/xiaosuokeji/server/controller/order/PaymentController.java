package com.xiaosuokeji.server.controller.order;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.order.Payment;
import com.xiaosuokeji.server.service.order.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 还款Controller
 * Created by kunye on 2018/05/06.
 */
@Controller
@XSLog
@XSExceptionHandler
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = "/admin/order/payment", method = RequestMethod.GET)
	public String index(Model model, Payment payment) {
		if(payment.getPage() == null) {
			payment.setPage(1L);
		}
		model.addAttribute("search", payment);
		model.addAttribute("pageModel", paymentService.listAndCount(payment));
		return "admin/order/payment";
	}

	@RequestMapping(value = "/admin/order/payment/get", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult get(Payment payment) throws XSBusinessException {
		return XSServiceResult.build().data(paymentService.get(payment));
	}

	@RequestMapping(value = "/admin/order/payment/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(Payment.Save.class) Payment payment) throws XSBusinessException {
		paymentService.save(payment);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/order/payment/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(Payment payment) throws XSBusinessException {
		paymentService.remove(payment);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/order/payment/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(Payment.Update.class) Payment payment) throws XSBusinessException {
		paymentService.update(payment);
		return XSServiceResult.build();
	}
}
