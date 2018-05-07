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
@RequestMapping("/admin/product/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index() {
		return "admin/product/category";
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult tree() {
		return XSServiceResult.build().data(categoryService.tree(0L));
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult get(Category category) throws XSBusinessException {
		return XSServiceResult.build().data(categoryService.get(category));
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult save(@Validated(Category.Save.class) Category category) throws XSBusinessException {
		categoryService.save(category);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult remove(Category category) throws XSBusinessException {
		categoryService.remove(category);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult update(@Validated(Category.Update.class) Category category)
			throws XSBusinessException {
		categoryService.update(category);
		return XSServiceResult.build();
	}
}
