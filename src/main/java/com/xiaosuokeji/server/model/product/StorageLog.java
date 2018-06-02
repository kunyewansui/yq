package com.xiaosuokeji.server.model.product;

import com.xiaosuokeji.server.model.base.BaseModel;

/**
 * 库存日志
 * Created by kunye on 2018/06/02.
 */
public class StorageLog extends BaseModel {

	/**
	 * id
	 */
	private Long id;

	/**
	 * 员工id
	 */
	private Long staffId;

	private String staffName;

	/**
	 * 产品ID
	 */
	private Long productId;

	private String productCode;


	/**
	 * 类型，0-档口，1-工厂
	 */
	private Integer type;

	/**
	 * 库存变化
	 */
	private Long stock;

	/**
	 * 说明
	 */
	private String descn;

	private Integer action; //动作，0-入库，1-出库

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getStaffId() {
		return staffId;
	}
	
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}

	public Long getStock() {
		return stock;
	}
	
	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Integer getAction() {
		return action;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescn() {
		return descn;
	}
	
	public void setDescn(String descn) {
		this.descn = descn;
	}
}
