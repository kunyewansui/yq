package com.xiaosuokeji.server.controller.merchant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.merchant.Merchant;
import com.xiaosuokeji.server.model.order.Order;
import com.xiaosuokeji.server.model.order.Payment;
import com.xiaosuokeji.server.service.merchant.MerchantService;
import com.xiaosuokeji.server.service.order.OrderService;
import com.xiaosuokeji.server.service.order.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商户Controller
 * Created by kunye on 2018/05/06.
 */
@Controller
@XSLog
@XSExceptionHandler
@RequestMapping("/admin/merchant/merchant")
public class MerchantController {

	@Autowired
	private MerchantService merchantService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model, Merchant merchant) {
		if(merchant.getPage() == null) {
			merchant.setPage(1L);
		}
		model.addAttribute("search", merchant);
		model.addAttribute("pageModel", merchantService.listAndCount(merchant));
		return "admin/merchant/merchant";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public XSServiceResult list(Merchant merchant) throws XSBusinessException {
		if(merchant.getPage() == null) {
			merchant.setPage(1L);
		}
		return XSServiceResult.build().data(merchantService.listAndCount(merchant));
	}

	@RequestMapping(value = "/toadd", method = RequestMethod.GET)
	public String toadd() throws Exception {
		return "admin/merchant/merchant_add";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model, Merchant merchant) throws XSBusinessException, JsonProcessingException {
		model.addAttribute("merchant", merchantService.get(merchant));
		return "admin/merchant/merchant_detail";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(Merchant.Save.class) Merchant merchant) throws XSBusinessException {
		merchantService.save(merchant);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(Merchant merchant) throws XSBusinessException {
		//TODO 客户有欠款不能删，有订单进行中不能删
		merchantService.remove(merchant);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(Merchant.Update.class) Merchant merchant) throws XSBusinessException {
		merchantService.update(merchant);
		return XSServiceResult.build();
	}
}
