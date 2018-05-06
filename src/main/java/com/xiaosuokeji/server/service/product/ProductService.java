package com.xiaosuokeji.server.service.product;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.product.ProductConsts;
import com.xiaosuokeji.server.dao.product.ProductDao;
import com.xiaosuokeji.server.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public void save(Product product) throws XSBusinessException {
		productDao.save(product);
	}

	public void remove(Product product) throws XSBusinessException {
		Product existent = get(product);
		productDao.remove(existent);
	}

	public void update(Product product) throws XSBusinessException {
		Product existent = get(product);
		productDao.update(product);
	}

	public Product get(Product product) throws XSBusinessException {
		Product existent = productDao.get(product);
		if (existent == null) {
			throw new XSBusinessException(ProductConsts.PRODUCT_NOT_EXIST);
		}
		return existent;
	}

	public XSPageModel<Product> listAndCount(Product product) {
		product.setDefaultSort("id", "DESC");
		return XSPageModel.build(productDao.list(product), productDao.count(product));
	}
}
