package com.xiaosuokeji.server.model.base;

/**
 * 分类<br/>
 * Created by xuxiaowei on 2017/10/23.
 */
public class Category extends BaseModel {

    /**
     * id
     */
    private String id;

    /**
     * 键
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级
     */
    private Category parent;

    /**
     * 顺序
     */
    private Integer seq;

    /**
     * 展示，0否，1是
     */
    private Integer display;

    /**
     * 锁定，0否，1是
     */
    private Integer lock;

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

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
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
}
