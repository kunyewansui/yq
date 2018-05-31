package com.xiaosuokeji.server.dao.product;

import com.xiaosuokeji.server.model.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 产品Dao
 * Created by kunye on 2018/05/06.
 */
public interface ProductDao {

	int save(Product product);

	int remove(Product product);

	int update(Product product);

	int updateStock(Product product);

	Product get(Product product);

	Product getByCode(Product product);

	List<Product> list(Product product);

	List<Product> storageList(Product product);

	Long count(Product product);

	Long storageCount(Product product);

	Map<String,Object> statisticsStorage(@Param("type") Integer type);

}
