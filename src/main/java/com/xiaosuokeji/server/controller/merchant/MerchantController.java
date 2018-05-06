package com.xiaosuokeji.server.controller.merchant;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.merchant.Merchant;
import com.xiaosuokeji.server.service.merchant.MerchantService;
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
public class MerchantController {

	@Autowired
	private MerchantService merchantService;

	@RequestMapping(value = "/admin/merchant/merchant", method = RequestMethod.GET)
	public String index(Model model, Merchant merchant) {
		if(merchant.getPage() == null) {
			merchant.setPage(1L);
		}
		model.addAttribute("search", merchant);
		model.addAttribute("pageModel", merchantService.listAndCount(merchant));
		return "admin/merchant/merchant";
	}

	@RequestMapping(value = "/admin/merchant/merchant/get", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult get(Merchant merchant) throws XSBusinessException {
		return XSServiceResult.build().data(merchantService.get(merchant));
	}

	@RequestMapping(value = "/admin/merchant/merchant/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(Merchant.Save.class) Merchant merchant) throws XSBusinessException {
		merchantService.save(merchant);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/merchant/merchant/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(Merchant merchant) throws XSBusinessException {
		merchantService.remove(merchant);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/merchant/merchant/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(Merchant.Update.class) Merchant merchant) throws XSBusinessException {
		merchantService.update(merchant);
		return XSServiceResult.build();
	}
}
