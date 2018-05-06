package com.xiaosuokeji.server.model.product;

import com.xiaosuokeji.server.model.base.BaseModel;

/**
 * 产品类型
 * Created by kunye on 2018/05/06.
 */
public class Category extends BaseModel {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 类型名称
	 */
	private String name;

	/**
	 * 上级
	 */
	private Long pId;

	/**
	 * 等级
	 */
	private Integer level;

	/**
	 * 排序
	 */
	private Integer seq;

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

	public Long getPId() {
		return pId;
	}
	
	public void setPId(Long pId) {
		this.pId = pId;
	}

	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}
