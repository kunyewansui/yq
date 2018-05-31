package com.xiaosuokeji.server.model.product;

import com.xiaosuokeji.server.model.article.Article;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

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
	@NotNull(message = "产品名称不能为空", groups = Save.class)
	@Length(min = 1, max = 255, message = "产品名称长度为1-255个字符", groups = {Save.class, Update.class})
	private String name;

	/**
	 * 类型
	 */
	@NotNull(message = "产品类型不能为空", groups = {Save.class})
	private Long cateId;

	private String cateName;

	/**
	 * 款号
	 */
	@NotNull(message = "款号不能为空", groups = Save.class)
	@Length(min = 1, max = 255, message = "款号长度为1-255个字符", groups = {Save.class, Update.class})
	private String code;

	/**
	 * 档口库存
	 */
	@NotNull(message = "档口库存不能为空", groups = {Save.class})
	private Long shopStock;

	/**
	 * 工厂库存
	 */
	@NotNull(message = "工厂库存不能为空", groups = {Save.class})
	private Long factoryStock;

	/**
	 * 成本
	 */
	@NotNull(message = "成本不能为空", groups = {Save.class})
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
	@NotNull(message = "出厂价不能为空", groups = {Save.class})
	private BigDecimal manuPrice;

	/**
	 * 版本
	 */
	private Integer version;

	private List<Long> cateList;

	private List<String> picList;

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

	public List<Long> getCateList() {
		return cateList;
	}

	public void setCateList(List<Long> cateList) {
		this.cateList = cateList;
	}

	public List<String> getPicList() {
		return picList;
	}

	public void setPicList(List<String> picList) {
		this.picList = picList;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public void setManuPrice(BigDecimal manuPrice) {
		this.manuPrice = manuPrice;
	}
}
