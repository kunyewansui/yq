package com.xiaosuokeji.server.model.product;

import com.xiaosuokeji.server.model.base.BaseModel;

/**
 * 产品颜色
 * Created by kunye on 2018/08/14.
 */
public class ProductStock extends BaseModel {

	/**
	 * 产品
	 */
	private Long id;

	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 颜色
	 */
	private String color;

	/**
	 * 库存
	 */
	private Long stock;

	public interface Save {}

	public interface Update {}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}

	public Long getStock() {
		return stock;
	}
	
	public void setStock(Long stock) {
		this.stock = stock;
	}
}
