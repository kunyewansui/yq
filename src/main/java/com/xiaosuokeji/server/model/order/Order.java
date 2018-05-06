package com.xiaosuokeji.server.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * Created by kunye on 2018/05/06.
 */
public class Order extends BaseModel {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 订单号
	 */
	private String orderno;

	/**
	 * 客户
	 */
	private Long merchantId;

	/**
	 * 订单金额
	 */
	private BigDecimal amount;

	/**
	 * 订单状态，0-已下单，1-交易中，2-已完成，3-已关闭
	 */
	private Integer status;

	/**
	 * 交货日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;

	/**
	 * 订单创建人
	 */
	private String creator;

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

	public Long getMerchantId() {
		return merchantId;
	}
	
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}
	
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
