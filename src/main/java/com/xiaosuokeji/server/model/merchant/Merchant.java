package com.xiaosuokeji.server.model.merchant;

import com.xiaosuokeji.server.model.base.BaseModel;

import java.math.BigDecimal;

/**
 * 商户
 * Created by kunye on 2018/05/06.
 */
public class Merchant extends BaseModel {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 商户名
	 */
	private String name;

	/**
	 * 性别，0-女，1-男
	 */
	private Integer sex;

	/**
	 * 商户联系电话
	 */
	private String mobile;

	/**
	 * 名片
	 */
	private String businessCard;

	/**
	 * 欠款
	 */
	private BigDecimal debt;

	/**
	 * 国家
	 */
	private String country;

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

	public Integer getSex() {
		return sex;
	}
	
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBusinessCard() {
		return businessCard;
	}
	
	public void setBusinessCard(String businessCard) {
		this.businessCard = businessCard;
	}

	public BigDecimal getDebt() {
		return debt;
	}
	
	public void setDebt(BigDecimal debt) {
		this.debt = debt;
	}

	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
}
