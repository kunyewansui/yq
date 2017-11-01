package com.xiaosuokeji.server.model.system;

import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 字典数据
 * Created by xuxiaowei on 2017/11/1.
 */
public class DictData extends BaseModel {

    /**
     * id
     */
    private Long id;

    /**
     * 字典
     */
    private Dict dict;

    /**
     * 值
     */
    @NotNull(message = "值不能为空", groups = Save.class)
    @Length(min = 1, max = 191, message = "值长度为1-191个字符", groups = {Save.class, Update.class})
    private String value;

    /**
     * 名称
     */
    @NotNull(message = "名称不能为空", groups = Save.class)
    @Length(min = 1, max = 255, message = "名称长度为1-255个字符", groups = {Save.class, Update.class})
    private String name;

    /**
     * 描述
     */
    @NotNull(message = "描述不能为空", groups = Save.class)
    @Length(min = 1, max = 255, message = "描述长度为1-255个字符", groups = {Save.class, Update.class})
    private String desc;

    /**
     * 锁定，0否，1是
     */
    private Integer lock;

    public interface Save {}

    public interface Update {}

    public DictData() {}

    public DictData(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dict getDict() {
        return dict;
    }

    public void setDict(Dict dict) {
        this.dict = dict;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }
}
