package com.xiaosuokeji.server.model.order;

import com.xiaosuokeji.server.model.base.BaseModel;

/**
 * 还款
 * Created by kunye on 2018/05/06.
 */
public class Payment extends BaseModel {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 订单号
	 */
	private String orderno;

	/**
	 * 商户
	 */
	private Long merchantId;

	/**
	 * 还款金额
	 */
	private BigDecimal amount;

	/**
	 * 备注说明
	 */
	private String remark;

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

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
