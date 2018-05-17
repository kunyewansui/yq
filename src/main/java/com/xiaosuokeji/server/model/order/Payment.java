package com.xiaosuokeji.server.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

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
	@NotNull(message = "商户不能为空", groups = Save.class)
	private Long merchantId;

	private String merchantName;

	/**
	 * 还款金额
	 */
	@NotNull(message = "还款金额不能为空", groups = Save.class)
	private BigDecimal amount;

	/**
	 * 还款时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date refundTime;

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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
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
