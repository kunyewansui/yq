package com.xiaosuokeji.server.controller.product;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.product.Product;
import com.xiaosuokeji.server.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 产品Controller
 * Created by kunye on 2018/05/06.
 */
@Controller
@XSLog
@XSExceptionHandler
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/admin/product/product", method = RequestMethod.GET)
	public String index(Model model, Product product) {
		if(product.getPage() == null) {
			product.setPage(1L);
		}
		model.addAttribute("search", product);
		model.addAttribute("pageModel", productService.listAndCount(product));
		return "admin/product/product";
	}

	@RequestMapping(value = "/admin/product/product/get", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult get(Product product) throws XSBusinessException {
		return XSServiceResult.build().data(productService.get(product));
	}

	@RequestMapping(value = "/admin/product/product/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(Product.Save.class) Product product) throws XSBusinessException {
		productService.save(product);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/product/product/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(Product product) throws XSBusinessException {
		productService.remove(product);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/product/product/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(Product.Update.class) Product product) throws XSBusinessException {
		productService.update(product);
		return XSServiceResult.build();
	}
}
