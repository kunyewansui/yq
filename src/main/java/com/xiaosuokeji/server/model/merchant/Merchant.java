package com.xiaosuokeji.server.model.merchant;

import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
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
	@NotNull(message = "商户名不能为空", groups = Save.class)
	@Length(min = 1, max = 255, message = "商户名长度为1-255个字符", groups = {Save.class, Update.class})
	private String name;

	/**
	 * 性别，0-女，1-男
	 */
	private Integer sex;

	/**
	 * 商户联系电话
	 */
	@NotNull(message = "联系电话不能为空", groups = Save.class)
	private String mobile;

	/**
	 * 名片
	 */
	private String businessCard;

	/**
	 * 欠款
	 */
	@NotNull(message = "欠款不能为空", groups = {Save.class})
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
