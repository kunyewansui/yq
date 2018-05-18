package com.xiaosuokeji.server.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.merchant.Merchant;
import com.xiaosuokeji.server.model.product.Product;
import com.xiaosuokeji.server.service.product.CategoryService;
import com.xiaosuokeji.server.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/admin/product/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model, Product product) throws JsonProcessingException {
		if(product.getPage() == null) {
			product.setPage(1L);
		}
		model.addAttribute("search", product);
		model.addAttribute("pageModel", productService.listAndCount(product));
		model.addAttribute("cateTree", XSJackson.toJsonString(categoryService.tree(0L)));
		return "admin/product/product";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public XSServiceResult list(Product product) throws XSBusinessException {
		if(product.getPage() == null) {
			product.setPage(1L);
		}
		return XSServiceResult.build().data(productService.listAndCount(product));
	}

	@RequestMapping(value = "/toadd", method = RequestMethod.GET)
	public String toadd(Model model) throws Exception {
		model.addAttribute("cateTree", XSJackson.toJsonString(categoryService.tree(0L)));
		return "admin/product/product_add";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model, Product product) throws XSBusinessException, JsonProcessingException {
		model.addAttribute("product", productService.get(product));
		model.addAttribute("cateTree", XSJackson.toJsonString(categoryService.tree(0L)));
		return "admin/product/product_detail";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(Product.Save.class) @RequestBody Product product) throws XSBusinessException, JsonProcessingException {
		productService.save(product);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(Product product) throws XSBusinessException {
		productService.remove(product);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(Product.Update.class) @RequestBody Product product) throws XSBusinessException, JsonProcessingException {
		productService.update(product);
		return XSServiceResult.build();
	}
}
