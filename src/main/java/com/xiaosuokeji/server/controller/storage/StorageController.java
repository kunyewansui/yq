package com.xiaosuokeji.server.controller.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.product.Product;
import com.xiaosuokeji.server.service.product.CategoryService;
import com.xiaosuokeji.server.service.product.ProductService;
import com.xiaosuokeji.server.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 产品Controller
 * Created by kunye on 2018/05/06.
 */
@Controller
@XSLog
@XSExceptionHandler
@RequestMapping("/admin/storage/storage")
public class StorageController {

	@Autowired
	private ProductService productService;

	/**
	 * @param type 0-全部，1-档口，2-工厂
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model, @RequestParam(defaultValue = "0") Integer type) throws JsonProcessingException {
		if(type != 1 && type !=2) type=0;
		model.addAttribute("statistics", productService.statisticsStorage(type));
		model.addAttribute("type", type);
		return "admin/storage/storage";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public XSServiceResult list(Product product) throws XSBusinessException {
		if(product.getPage() == null) {
			product.setPage(1L);
		}
		if(!CollectionUtils.isBlank(product.getDynamic()) && product.getDynamic().get("type")!=null){
			int type = Integer.parseInt(product.getDynamic().get("type"));
			if (type == 1) {
				product.setDefaultSort("a.shop_stock", "DESC");
			} else if (type ==2) {
				product.setDefaultSort("a.factory_stock", "DESC");
			}
		}
		return XSServiceResult.build().data(productService.StoragelistAndCount(product));
	}

	@RequestMapping(value = "/shop/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult shopupdate(Product product) throws XSBusinessException {
		Product p = new Product();
		p.setId(product.getId());
		p.setShopStock(product.getStock());
		productService.updateStock(p);
		return XSServiceResult.build();
	}

	@RequestMapping(value = "/factory/update", method = RequestMethod.POST)
	@ResponseBody
	public XSServiceResult factoryupdate(Product product) throws XSBusinessException {
		Product p = new Product();
		p.setId(product.getId());
		p.setFactoryStock(product.getStock());
		productService.updateStock(p);
		return XSServiceResult.build();
	}
}
