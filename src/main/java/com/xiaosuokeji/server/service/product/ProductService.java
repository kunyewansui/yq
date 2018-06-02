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
import com.xiaosuokeji.server.model.product.StorageLog;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.service.security.SecResourceService;
import com.xiaosuokeji.server.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品ServiceImpl
 * Created by kunye on 2018/05/06.
 */
@Service
public class ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductDao productDao;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private StorageLogService storageLogService;

	public void save(Product product) throws XSBusinessException, JsonProcessingException {
		if(!CollectionUtils.isBlank(product.getPicList())){
			product.setPics(XSJackson.toJsonString(product.getPicList()));
		}else{
			product.setPics("[]");
		}

		try {
			product.setCode(product.getCode().trim());
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
			if(product.getCode()!=null) product.setCode(product.getCode().trim());
			productDao.update(product);
		} catch (DuplicateKeyException e) {
			throw new XSBusinessException(ProductConsts.PRODUCT_CODE_EXISTED);
		}
	}

	public void updateStock(Product product) throws XSBusinessException {
		Product p = get(product);
		if(product.getShopStock()!=null){
			if(p.getShopStock()+product.getShopStock()<0){
				throw new XSBusinessException(ProductConsts.PRODUCT_STOCK_LACK);
			}
		}
		if(product.getFactoryStock()!=null){
			if(p.getFactoryStock()+product.getFactoryStock()<0){
				throw new XSBusinessException(ProductConsts.PRODUCT_STOCK_LACK);
			}
		}
		product.setVersion(p.getVersion());
		productDao.updateStock(product);
		//库存日志记录
		StorageLog storageLog = new StorageLog();
		storageLog.setProductId(product.getId());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecStaff secStaff = (SecStaff) authentication.getPrincipal();
		storageLog.setStaffId(secStaff.getId());
		if(product.getShopStock()!=null){
			storageLog.setType(0);
			storageLog.setStock(product.getShopStock());
		}else{
			storageLog.setType(1);
			storageLog.setStock(product.getFactoryStock());
		}
		try {
			storageLogService.save(storageLog);
		} catch (XSBusinessException e) {
			logger.info("storage logging error!");
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

	public XSPageModel<Product> StoragelistAndCount(Product product) {
		if(CollectionUtils.isBlank(product.getSort())){
			product.setDefaultSort("a.id", "DESC");
		}
		return XSPageModel.build(productDao.storageList(product), productDao.storageCount(product));
	}

	/**
	 * 统计档口和工厂的库存情况
	 * @param type 0-全部，1-档口，2-工厂
	 */
	public Map<String, Object> statisticsStorage(Integer type){
		return productDao.statisticsStorage(type);
	}

}
