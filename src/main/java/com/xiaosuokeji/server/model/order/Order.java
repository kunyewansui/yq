package com.xiaosuokeji.server.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
	private String orderNo;

	/**
	 * 客户
	 */
	@NotNull(message = "商户不能为空", groups = Save.class)
	private Long merchantId;

	private String merchantName;

	private String merchantMobile;

	/**
	 * 订单金额
	 */
	@NotNull(message = "订单金额不能为空", groups = Save.class)
	private BigDecimal amount;

	/**
	 * 预付款
	 */
	@NotNull(message = "预付款不能为空", groups = Save.class)
	private BigDecimal imprest;


	/**
	 * 订单状态，0-交易中，1-已完成，2-已关闭
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

	/**
	 * 备注
	 */
	private String remark;

	private List<OrderItem> orderItemList;

	public interface Save {}

	public interface Update {}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantMobile() {
		return merchantMobile;
	}

	public void setMerchantMobile(String merchantMobile) {
		this.merchantMobile = merchantMobile;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public BigDecimal getImprest() {
		return imprest;
	}

	public void setImprest(BigDecimal imprest) {
		this.imprest = imprest;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}
