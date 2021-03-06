package com.xiaosuokeji.server.model.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xiaosuokeji.framework.annotation.XSAutoDesc;
import com.xiaosuokeji.framework.intf.XSTreeable;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片分类
 * Created by xuxiaowei on 2017/10/23.
 */
public class ImageCategory extends BaseModel implements XSTreeable<String, ImageCategory> {

    /**
     * id
     */
    private String id;

    /**
     * 键
     */
    @NotNull(message = "键不能为空", groups = Save.class)
    @Length(min = 1, max = 191, message = "键长度为1-191个字符", groups = {Save.class, Update.class})
    private String key;

    /**
     * 名称
     */
    @NotNull(message = "名称不能为空", groups = Save.class)
    @Length(min = 1, max = 255, message = "名称长度为1-255个字符", groups = {Save.class, Update.class})
    private String name;

    /**
     * 父级
     */
    private ImageCategory parent;

    /**
     * 顺序
     */
    @NotNull(message = "顺序不能为空", groups = Save.class)
    private Integer seq;

    /**
     * 展示，0否，1是
     */
    @XSAutoDesc("imageCategoryDisplay")
    private Integer display;

    /**
     * 锁定，0否，1是
     */
    @XSAutoDesc("imageCategoryLock")
    private Integer lock;

    /**
     * 子级列表
     */
    private List<ImageCategory> children;

    /**
     * 分类列表
     */
    private List<ImageCategory> list;

    public interface Save {}

    public interface Update {}

    public ImageCategory() {}

    public ImageCategory(String id) {
        this.id = id;
    }

    @JsonIgnore
    @Override
    public String getNodeId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getParentNodeId() {
        if (parent != null) {
            return parent.getId();
        }
        return null;
    }

    @Override
    public void addChild(ImageCategory imageCategory) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(imageCategory);
    }

    @Override
    public List<ImageCategory> getChildren() {
        return children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageCategory getParent() {
        return parent;
    }

    public void setParent(ImageCategory parent) {
        this.parent = parent;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public void setChildren(List<ImageCategory> children) {
        this.children = children;
    }

    public List<ImageCategory> getList() {
        return list;
    }

    public void setList(List<ImageCategory> list) {
        this.list = list;
    }
}
