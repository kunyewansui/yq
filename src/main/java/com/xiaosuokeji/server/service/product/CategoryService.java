package com.xiaosuokeji.server.service.product;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.product.CategoryConsts;
import com.xiaosuokeji.server.dao.product.CategoryDao;
import com.xiaosuokeji.server.model.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品类型ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	public void save(Category category) throws XSBusinessException {
		categoryDao.save(category);
	}

	public void remove(Category category) throws XSBusinessException {
		Category existent = get(category);
		categoryDao.remove(existent);
	}

	public void update(Category category) throws XSBusinessException {
		Category existent = get(category);
		categoryDao.update(category);
	}

	public Category get(Category category) throws XSBusinessException {
		Category existent = categoryDao.get(category);
		if (existent == null) {
			throw new XSBusinessException(CategoryConsts.CATEGORY_NOT_EXIST);
		}
		return existent;
	}

	public XSPageModel<Category> listAndCount(Category category) {
		category.setDefaultSort("id", "DESC");
		return XSPageModel.build(categoryDao.list(category), categoryDao.count(category));
	}
}
