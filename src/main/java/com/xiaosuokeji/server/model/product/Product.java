package com.xiaosuokeji.server.model.product;

import com.xiaosuokeji.server.model.base.BaseModel;

import java.math.BigDecimal;

/**
 * 产品
 * Created by kunye on 2018/05/06.
 */
public class Product extends BaseModel {

	/**
	 * 产品
	 */
	private Long id;

	/**
	 * 产品名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private Long cateId;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 档口库存
	 */
	private Long shopStock;

	/**
	 * 工厂库存
	 */
	private Long factoryStock;

	/**
	 * 成本
	 */
	private BigDecimal cost;

	/**
	 * 主图片
	 */
	private String image;

	/**
	 * 图片
	 */
	private String pics;

	/**
	 * 出厂价
	 */
	private BigDecimal manuPrice;

	public interface Save {}

	public interface Update {}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Long getCateId() {
		return cateId;
	}
	
	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public Long getShopStock() {
		return shopStock;
	}
	
	public void setShopStock(Long shopStock) {
		this.shopStock = shopStock;
	}

	public Long getFactoryStock() {
		return factoryStock;
	}
	
	public void setFactoryStock(Long factoryStock) {
		this.factoryStock = factoryStock;
	}

	public BigDecimal getCost() {
		return cost;
	}
	
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}

	public String getPics() {
		return pics;
	}
	
	public void setPics(String pics) {
		this.pics = pics;
	}

	public BigDecimal getManuPrice() {
		return manuPrice;
	}
	
	public void setManuPrice(BigDecimal manuPrice) {
		this.manuPrice = manuPrice;
	}
}
