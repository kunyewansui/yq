package com.xiaosuokeji.server.model.order;

import com.xiaosuokeji.server.model.base.BaseModel;
import com.xiaosuokeji.server.model.product.Product;

import java.math.BigDecimal;

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
	private String orderNo;

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

	/**
	 * 备注
	 */
	private String remark;

	private Product product;

	public interface Save {}

	public interface Update {}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTotal() {
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
