package com.xiaosuokeji.server.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xiaosuokeji.framework.intf.XSTreeable;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品类型
 * Created by kunye on 2018/05/06.
 */
public class Category extends BaseModel implements XSTreeable<Long, Category> {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 类型名称
	 */
	@NotNull(message = "类型名称不能为空", groups = Save.class)
	@Length(min = 1, max = 255, message = "类型名称为1-255个字符", groups = {Save.class, Update.class})
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
	@NotNull(message = "顺序不能为空", groups = Save.class)
	private Integer seq;

	private List<Category> children;

	@JsonIgnore
	@Override
	public Long getNodeId() {
		return id;
	}

	@JsonIgnore
	@Override
	public Long getParentNodeId() {
		return pId;
	}

	@Override
	public void addChild(Category category) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(category);
	}

	@Override
	public List<Category> getChildren() {
		return children;
	}

	public interface Save {}

	public interface Update {}

	public Category() {
	}

	public Category(Long id) {
		this.id = id;
	}

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

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}
