package com.xiaosuokeji.server.controller.product;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.product.Category;
import com.xiaosuokeji.server.service.product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 产品类型Controller
 * Created by kunye on 2018/05/06.
 */
@Controller
@XSLog
@XSExceptionHandler
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/admin/product/category", method = RequestMethod.GET)
	public String index(Model model, Category category) {
		if(category.getPage() == null) {
			category.setPage(1L);
		}
		model.addAttribute("search", category);
		model.addAttribute("pageModel", categoryService.listAndCount(category));
		return "admin/product/category";
	}

	@RequestMapping(value = "/admin/product/category/get", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult get(Category category) throws XSBusinessException {
		return XSServiceResult.build().data(categoryService.get(category));
	}

	@RequestMapping(value = "/admin/product/category/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(Category.Save.class) Category category) throws XSBusinessException {
		categoryService.save(category);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/product/category/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(Category category) throws XSBusinessException {
		categoryService.remove(category);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/admin/product/category/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(Category.Update.class) Category category) throws XSBusinessException {
		categoryService.update(category);
		return XSServiceResult.build();
	}
}
