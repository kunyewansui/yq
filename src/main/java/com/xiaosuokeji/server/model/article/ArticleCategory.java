package com.xiaosuokeji.server.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xiaosuokeji.framework.intf.XSTreeable;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章分类
 * Created by xuxiaowei on 2017/10/28.
 */
public class ArticleCategory extends BaseModel implements XSTreeable<String> {

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
     * 预览前缀
     */
    @NotNull(message = "预览前缀不能为空", groups = Save.class)
    @Length(min = 1, max = 255, message = "预览前缀长度为1-255个字符", groups = {Save.class, Update.class})
    private String prefix;

    /**
     * 图标
     */
    @Length(max = 255, message = "图标长度最多为255个字符", groups = {Save.class, Update.class})
    private String icon;

    /**
     * 父级
     */
    private ArticleCategory parent;

    /**
     * 顺序
     */
    @NotNull(message = "顺序不能为空", groups = Save.class)
    private Integer seq;

    /**
     * 展示，0否，1是
     */
    private Integer display;

    /**
     * 锁定，0否，1是
     */
    private Integer lock=0;

    /**
     * 子级列表
     */
    private List<XSTreeable<String>> children;

    /**
     * 分类列表
     */
    private List<XSTreeable<String>> list;

    public interface Save {}

    public interface Update {}

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
    public void addChild(XSTreeable<String> child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    @Override
    public List<XSTreeable<String>> getChildren() {
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArticleCategory getParent() {
        return parent;
    }

    public void setParent(ArticleCategory parent) {
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

    public void setChildren(List<XSTreeable<String>> children) {
        this.children = children;
    }

    public List<XSTreeable<String>> getList() {
        return list;
    }

    public void setList(List<XSTreeable<String>> list) {
        this.list = list;
    }
}