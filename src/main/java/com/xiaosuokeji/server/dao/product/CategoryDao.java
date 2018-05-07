package com.xiaosuokeji.server.dao.product;

import com.xiaosuokeji.server.model.product.Category;
import java.util.List;

/**
 * 产品类型Dao
 * Created by kunye on 2018/05/06.
 */
public interface CategoryDao {

	int save(Category category);

	int remove(Category category);

	int update(Category category);

	Category get(Category category);

	List<Category> list(Category category);

	Long count(Category category);

	List<Category> listCombo(Category category);
}
