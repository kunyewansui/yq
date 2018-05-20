package com.xiaosuokeji.server.service.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.common.CommonConsts;
import com.xiaosuokeji.server.constant.product.ProductConsts;
import com.xiaosuokeji.server.dao.product.ProductDao;
import com.xiaosuokeji.server.model.product.Category;
import com.xiaosuokeji.server.model.product.Product;
import com.xiaosuokeji.server.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private CategoryService categoryService;

	public void save(Product product) throws XSBusinessException, JsonProcessingException {
		if(!CollectionUtils.isBlank(product.getPicList())){
			product.setPics(XSJackson.toJsonString(product.getPicList()));
		}else{
			product.setPics("[]");
		}

		try {
			productDao.save(product);
		} catch (DuplicateKeyException e) {
			throw new XSBusinessException(ProductConsts.PRODUCT_CODE_EXISTED);
		}
	}

	public void remove(Product product) throws XSBusinessException {
		Product existent = get(product);
		productDao.remove(existent);
	}

	public void update(Product product) throws XSBusinessException, JsonProcessingException {
		Product existent = get(product);
		if(product.getPicList()!=null){
			product.setPics(XSJackson.toJsonString(product.getPicList()));
		}
		try {
			productDao.update(product);
		} catch (DuplicateKeyException e) {
			throw new XSBusinessException(ProductConsts.PRODUCT_CODE_EXISTED);
		}
	}

	public Product get(Product product) throws XSBusinessException {
		Product existent = productDao.get(product);
		if (existent == null) {
			throw new XSBusinessException(ProductConsts.PRODUCT_NOT_EXIST);
		}
		try {
			List<String> picList = XSJackson.toObject(existent.getPics(), ArrayList.class, String.class);
			existent.setPicList(picList);
		} catch (IOException e) {
			throw new XSBusinessException(CommonConsts.SYSTEM_ERROR);
		}
		return existent;
	}

	public XSPageModel<Product> listAndCount(Product product) {
		product.setDefaultSort("a.id", "DESC");
		if(product.getCateId()!=null){
			List<Long> cateList = categoryService.listSubTree(product.getCateId());
			product.setCateList(cateList);
		}
		return XSPageModel.build(productDao.list(product), productDao.count(product));
	}

    public Product getByCode(Product product) throws XSBusinessException {
		Product existent = productDao.getByCode(product);
		if (existent == null) {
			throw new XSBusinessException(ProductConsts.PRODUCT_NOT_EXIST);
		}
		return existent;
    }
}
