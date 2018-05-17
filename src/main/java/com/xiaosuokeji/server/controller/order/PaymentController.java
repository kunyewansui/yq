package com.xiaosuokeji.server.controller.order;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.order.Payment;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.service.order.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 还款Controller
 * Created by kunye on 2018/05/06.
 */
@Controller
@XSLog
@XSExceptionHandler
@RequestMapping("/admin/order/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "admin/order/payment";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public XSServiceResult list(Payment payment) throws XSBusinessException {
		if(payment.getPage() == null) {
			payment.setPage(1L);
		}
		return XSServiceResult.build().data(paymentService.listAndCount(payment));
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult get(Payment payment) throws XSBusinessException {
		return XSServiceResult.build().data(paymentService.get(payment));
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(Payment.Save.class) Payment payment, HttpServletRequest request) throws XSBusinessException {
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
				.getAttribute("SPRING_SECURITY_CONTEXT");
		SecStaff secStaff = (SecStaff) securityContextImpl.getAuthentication().getPrincipal();
		payment.setCreator(secStaff.getName());
		paymentService.save(payment);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(Payment payment) throws XSBusinessException {
		paymentService.remove(payment);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(Payment.Update.class) Payment payment) throws XSBusinessException {
		paymentService.update(payment);
		return XSServiceResult.build();
	}
}
