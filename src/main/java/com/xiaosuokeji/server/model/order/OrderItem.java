package com.xiaosuokeji.server.model.order;

import com.xiaosuokeji.server.model.base.BaseModel;

/**
 * 订单详情
 * Created by kunye on 2018/05/06.
 */
public class OrderItem extends BaseModel {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 订单号
	 */
	private String orderno;

	/**
	 * 产品
	 */
	private Long productId;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 总额
	 */
	private BigDecimal total;

	public interface Save {}

	public interface Update {}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderno() {
		return orderno;
	}
	
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotal() {
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
