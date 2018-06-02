package com.xiaosuokeji.server.dao.product;

import com.xiaosuokeji.server.model.product.StorageLog;
import java.util.List;

/**
 * 库存日志Dao
 * Created by kunye on 2018/06/02.
 */
public interface StorageLogDao {

	int save(StorageLog storageLog);

	List<StorageLog> list(StorageLog storageLog);

	Long count(StorageLog storageLog);
}
