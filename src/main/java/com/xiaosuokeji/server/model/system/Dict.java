package com.xiaosuokeji.server.model.system;

import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 字典
 * Created by xuxiaowei on 2017/11/1.
 */
public class Dict extends BaseModel {

    /**
     * id
     */
    private Long id;

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
     * 锁定，0否，1是
     */
    private Integer lock;

    /**
     * 字典列表
     */
    private List<Dict> list;

    public interface Save {}

    public interface Update {}

    public Dict() {}

    public Dict(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public List<Dict> getList() {
        return list;
    }

    public void setList(List<Dict> list) {
        this.list = list;
    }
}
