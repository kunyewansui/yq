package com.xiaosuokeji.server.dao.product;

import com.xiaosuokeji.server.model.product.Product;
import java.util.List;

/**
 * 产品Dao
 * Created by kunye on 2018/05/06.
 */
public interface ProductDao {

	int save(Product product);

	int remove(Product product);

	int update(Product product);

	Product get(Product product);

	Product getByCode(Product product);

	List<Product> list(Product product);

	Long count(Product product);
}
