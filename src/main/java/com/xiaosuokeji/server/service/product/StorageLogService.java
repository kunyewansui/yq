package com.xiaosuokeji.server.service.product;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.dao.product.StorageLogDao;
import com.xiaosuokeji.server.model.product.Product;
import com.xiaosuokeji.server.model.product.StorageLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 库存日志ServiceImpl
 * Created by kunye on 2018/06/02.
 */
@Service
public class StorageLogService {

	@Autowired
	private StorageLogDao storageLogDao;
	@Autowired
	private ProductService productService;

	public void save(StorageLog storageLog) throws XSBusinessException {
		Product product = productService.get(new Product(storageLog.getProductId()));
		String descn = (storageLog.getType()==0?"档口":"工厂")+"【"+product.getCode()+"】款产品"+(storageLog.getStock()>0?"入库":"出库")+"【"+Math.abs(storageLog.getStock())+"】件";
		storageLog.setDescn(descn);
		storageLogDao.save(storageLog);
	}

	public XSPageModel<StorageLog> listAndCount(StorageLog storageLog) {
		storageLog.setDefaultSort("a.id", "DESC");
		return XSPageModel.build(storageLogDao.list(storageLog), storageLogDao.count(storageLog));
	}
}
